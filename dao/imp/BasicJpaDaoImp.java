package com.thtf.dao.imp;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceException;
import javax.persistence.Query;

import org.hibernate.Session;
import org.springframework.orm.jpa.JpaCallback;
import org.springframework.orm.jpa.support.JpaDaoSupport;
import org.springframework.transaction.annotation.Transactional;

import com.thtf.dao.BasicDao;
import com.thtf.entity.BasicObject;
import com.thtf.util.StringUtil;

public class BasicJpaDaoImp<T extends BasicObject, M extends java.io.Serializable>
		extends JpaDaoSupport implements BasicDao<T, M> {

	@Override
	@Transactional
	public void delete(T t) {
		// TODO Auto-generated method stub
		super.getJpaTemplate().remove(getJpaTemplate().merge(t));
	}

	@Override
	@Transactional
	public void deleteById(M id, Class<T> entityClass) {
		// TODO Auto-generated method stub
		T t = this.getById(id, entityClass);
		super.getJpaTemplate().remove(t);
	}

	@Override
	@Transactional
	public void deleteAll(List<T> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			for (T t : list) {
				super.getJpaTemplate().remove(getJpaTemplate().merge(t));
			}
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<Object[]> excuteQuerySQL(final String sql) {
		// TODO Auto-generated method stub
		List<Object[]> list = super.getJpaTemplate().executeFind(
				new JpaCallback() {
					@Override
					public Object doInJpa(EntityManager em)
							throws PersistenceException {
						// TODO Auto-generated method stub
						Query query = em.createNativeQuery(sql);
						List list = query.getResultList();
						return list;
					}
				});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public int excuteUpdateByParam(final String hql, final Object[] o) {
		// TODO Auto-generated method stub
		Integer i = (Integer) super.getJpaTemplate().execute(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				// TODO Auto-generated method stub
				Query query = em.createQuery(hql);
				if (o != null)
					for (int i = 0; i < o.length; i++) {
						query.setParameter(i + 1, o[i]);
					}
				int i = query.executeUpdate();
				return i;
			}
		});
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	@Transactional
	public int excuteUpdateSQL(final String sql) {
		// TODO Auto-generated method stub
		Integer i = (Integer) super.getJpaTemplate().execute(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				// TODO Auto-generated method stub
				Query query = em.createNativeQuery(sql);
				int i = query.executeUpdate();
				return i;
			}
		});
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAll(final String sortOrder, final String orderBy,
			final Class<T> entityClass) {
		// TODO Auto-generated method stub
		List<T> list = super.getJpaTemplate().executeFind(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				// TODO Auto-generated method stub
				String jpql = " from " + entityClass.getSimpleName()
						+ " t order by t." + orderBy + " " + sortOrder;
				Query query = em.createQuery(jpql);
				return query.getResultList();
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getAllByPagination(final int start, final int pageSize,
			final String sortOrder, final String orderBy,
			final Class<T> entityClass) {
		// TODO Auto-generated method stub
		List<T> list = super.getJpaTemplate().executeFind(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				// TODO Auto-generated method stub
				Query query = em.createQuery(" from "
						+ entityClass.getSimpleName() + " t order by t."
						+ orderBy + " " + sortOrder);
				query.setFirstResult(start);
				query.setMaxResults(pageSize);
				return query.getResultList();
			}
		});
		return list;
	}

	@Override
	public T getById(M id, Class<T> entityClass) {
		// TODO Auto-generated method stub
		return super.getJpaTemplate().find(entityClass, id);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getByQuery(String hql, Object[] o) {
		// TODO Auto-generated method stub
		List<T> list = super.getJpaTemplate().find(hql, o);
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> getByQueryPagination(final int start, final int pageSize,
			final String hql, final Object[] o) {
		// TODO Auto-generated method stub
		List<T> list = super.getJpaTemplate().executeFind(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				// TODO Auto-generated method stub
				Query query = em.createQuery(hql);
				if (o != null)
					for (int i = 0; i < o.length; i++) {
						query.setParameter(i + 1, o[i]);
					}
				query.setFirstResult(start);
				query.setMaxResults(pageSize);
				return query.getResultList();
			}
		});
		return list;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getDataCount(final Class<T> entityClass) {
		// TODO Auto-generated method stub
		final String jpql = "SELECT COUNT(*) FROM "
				+ entityClass.getSimpleName();
		Integer i = (Integer) super.getJpaTemplate().execute(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				// TODO Auto-generated method stub
				Query query = em.createQuery(jpql);
				Long count = (Long) query.getSingleResult();
				return count.intValue();
			}
		});
		return i;
	}

	@SuppressWarnings("unchecked")
	@Override
	public int getDataCount(String hql, final Object[] o) {
		// TODO Auto-generated method stub
		final String jpql = "SELECT COUNT(*) " + StringUtil.removeOrders(hql);
		Integer i = (Integer) super.getJpaTemplate().execute(new JpaCallback() {
			@Override
			public Object doInJpa(EntityManager em) throws PersistenceException {
				// TODO Auto-generated method stub
				Query query = em.createQuery(jpql);
				if (o != null)
					for (int i = 0; i < o.length; i++) {
						query.setParameter(i + 1, o[i]);
					}
				Long count = (Long) query.getSingleResult();
				return count.intValue();
			}
		});
		return i;
	}

	@Override
	public Session getSessionMustBeClosed() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional
	public void save(final T t) {
		// TODO Auto-generated method stub
		super.getJpaTemplate().persist(t);
	}

	@Override
	@Transactional
	public void saveAll(List<T> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			for (T t : list) {
				super.getJpaTemplate().merge(t);
			}
		}
	}

	@Override
	@Transactional
	public void update(T t) {
		// TODO Auto-generated method stub
		super.getJpaTemplate().merge(t);
	}

	@Override
	@Transactional
	public void updateAll(List<T> list) {
		// TODO Auto-generated method stub
		if (list != null) {
			for (T t : list) {
				super.getJpaTemplate().merge(t);
			}
		}
	}

}
