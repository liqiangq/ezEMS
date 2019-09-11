package com.thtf.quartz.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.activation.DataHandler;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;
import javax.mail.util.ByteArrayDataSource;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.thtf.entity.EpcReport;
import com.thtf.service.ReportService;
import com.thtf.util.DateUtil;
import com.thtf.util.ExcelUtil;
import com.thtf.util.PdfUtil;
import com.thtf.util.StringUtil;

/**
 * @author 孙刚 E-mail:sungang01@thtf.com.cn
 * @version 创建时间：2010-12-13 上午09:19:29 类说明
 */
public class MailService {

	private ReportService reportService;
	private JavaMailSender mailSender;
	private final PdfUtil pdfUtil = new PdfUtil();
	private final ExcelUtil excelUtil = new ExcelUtil();
	private String fromEmail;

	public void setReportService(ReportService reportService) {
		this.reportService = reportService;
	}

	public void setMailSender(JavaMailSender mailSender) {
		this.mailSender = mailSender;
	}
	
	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public void sendMail() {
		System.out.println("定时任务开始.....");
		List<EpcReport> list = this.getReportPlans();
		Map<String,byte[]> files = null;
		List<Map<String, String>> datas = null;
		String reportType = "";
		if(list!=null){
			for(EpcReport er:list){
				reportType = er.getReportType();
				if(reportType!=null){
					System.out.println("开始遍历.....");
					datas = this.getReportDatas(er);
					files = new HashMap<String,byte[]>();
					if(reportType.indexOf("pdf")>-1){
						files.put("pdf",pdfUtil.MarkPdf(datas));
					}
					if(reportType.indexOf("xls")>-1){
						files.put("xls",excelUtil.writeExcel(datas));
					}
				}
				this.sendMimeMessage(er.getEmail(), files,"订阅的ezEMS统计信息_"+er.getName(), er.getName()+"_"+DateUtil.date2String(new Date(), "yyyyMMdd"));
			}
		}
		System.out.println("定时任务结束.....");
	}

	private List<Map<String, String>> getReportDatas(EpcReport epcReport) {
		String itemIds = epcReport.getItemIds();
		List<String> ids = null;
		if(itemIds!=null){
			String[] s = itemIds.split(",");
			if(s!=null){
				ids = new ArrayList<String>();
				for(String ss:s){
					if(!StringUtil.isNull(ss))
						ids.add(ss);
				}
			}
		}
		return reportService.report(null, epcReport.getDateType(), epcReport.getItemType(),epcReport.getDataType(), ids,epcReport.getUnitId());
	}
	
	private List<EpcReport> getReportPlans(){
		Calendar rightNow = Calendar.getInstance();
		int month = rightNow.get(Calendar.MONTH)+1;
		int day = rightNow.get(Calendar.DAY_OF_MONTH);
		String jpql = "from EpcReport t ";
		if(month != 1&&day == 1){
			jpql += "where t.dateType='day' or t.dateType='month' ";
		}else{
			jpql += "where t.dateType='day' ";
		}
		System.out.println(jpql);
		return reportService.getByQuery(jpql,null);
	}
	
	synchronized private void sendMimeMessage(final String email,final Map<String,byte[]> files,final String subjectName,final String fileName){
        //附件文件集合
        MimeMessagePreparator mimeMail = new MimeMessagePreparator() {
            public void prepare(MimeMessage mimeMessage) throws MessagingException {
                mimeMessage.setRecipient(Message.RecipientType.TO, 
                        new InternetAddress(email));
                mimeMessage.setFrom(new InternetAddress(fromEmail));
                mimeMessage.setSubject(subjectName, "UTF-8"); 
                Multipart mp = new MimeMultipart();
                
                //向Multipart添加正文
                MimeBodyPart content = new MimeBodyPart();
                content.setText("内含ezEMS定时发送的统计信息，请查收!");
                
                //向MimeMessage添加（Multipart代表正文）
                mp.addBodyPart(content);
                
                //向Multipart添加附件
                if(files!=null){
                	for(Iterator<String> it = files.keySet().iterator();it.hasNext();){
                		String ext = it.next();
                		MimeBodyPart mdp = new MimeBodyPart(); 
                		mdp.setContent(content, "text/html;charset=\"UTF-8\"");
                        DataHandler dh = new DataHandler(new ByteArrayDataSource(files.get(ext),"application/octet-stream"));
                        mdp.setDataHandler(dh);   
                        try {
							mdp.setFileName(MimeUtility.encodeText(fileName+"."+ext));
						} catch (UnsupportedEncodingException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
                        mp.addBodyPart(mdp); 
                	}
                }
                files.clear();
                //向Multipart添加MimeMessage
                mimeMessage.setContent(mp);
                mimeMessage.setSentDate(new Date());
            }
        };
        //发送带附件的邮件
        mailSender.send(mimeMail);
        System.out.println("成功发送带附件邮件!");
    }
}
