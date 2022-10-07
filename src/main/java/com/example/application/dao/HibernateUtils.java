package com.example.application.dao;
import java.io.Serializable;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.example.application.entity.Product_subcategory;
import com.example.application.entity.Products;
import com.example.application.entity.Subcategory;
import com.example.application.entity.Users;


@Repository
public class HibernateUtils {
	@Autowired
	public SessionFactory sessionFactory;

	public Session getSession() {
		return sessionFactory.openSession();
	}

	public void closeSession(Session session) {
		if (session.isOpen())
			session.close();
	}

	public <T> T save(T entity) {
		Session session = getSession();
		try {
			Transaction tx = session.beginTransaction();
			Serializable id = session.save(entity);
			tx.commit();
			return (T) id;

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return null;
	}

	public <T> void update(T entity) {
		Session session = getSession();
		try {
			Transaction tx = session.beginTransaction();
			session.update(entity);
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
	}

	public <T> void delete(T entity) {
		Session session = getSession();
		try {
			Transaction tx = session.beginTransaction();
			session.delete(session.merge(entity));
			tx.commit();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}

	}

	@SuppressWarnings("unchecked")
	public <T> T findEntityById(T entity, Serializable id) {
		Session session = getSession();
		try {
			return (T) session.get(entity.getClass(), id);

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return null;
	}

	public <T> List<T> loadEntityByClassName(T entity) {
		Session session = getSession();
		try {
			Query<T> qry = session.createQuery("from " + entity.getClass().getName());
			return qry.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return null;
	}

	public <T> List<T> loadEntityByHql(String hql) {
		Session session = getSession();
		try {

			Query<T> qry = session.createSQLQuery(hql);
			return qry.list();

		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			closeSession(session);
		}
		return null;
	}
	public boolean checkEmail(String email) {
		Users users;
		try (Session session = sessionFactory.openSession()) {
			String hql = "FROM Users WHERE Email =: email";
			Query query = session.createQuery(hql);
			query.setParameter("email",email);
			users = (Users) query.getSingleResult();
			if(users==null) {
				return false;
			}
			else {
				return true;
			}
		} catch (Exception e) {
			throw e;
		}

	}
	public Users userByEmail(String email) {
		Users users;
		try (Session session = sessionFactory.openSession()) {
			String hql = "FROM Users WHERE Email =: email";
			Query query = session.createQuery(hql);
			query.setParameter("email",email);
			users = (Users) query.getSingleResult();
			return users;

		} catch (Exception e) {
			throw e;
		}
	}
	public boolean checkProduct(String name) {
		Products products;
		try (Session session = sessionFactory.openSession()) {
		String hql = "FROM Products WHERE Name =: name";
		Query query = session.createQuery(hql);
		query.setParameter("name",name);
		products = (Products) query.getSingleResult();
			if(products==null) {
				return false;
			}
			else {
				return true;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	public Products productByName(String name) {
		Products products;
		try (Session session = sessionFactory.openSession()) {
			String hql = "FROM Products WHERE Name =: name";
			Query query = session.createQuery(hql);
			query.setParameter("name",name);
			products = (Products) query.getSingleResult();
			return products;

		} catch (Exception e) {
			throw e;
		}
	}
	public Products productByCategory(String category) {
		Products products;
		try (Session session = sessionFactory.openSession()) {
			String hql = "FROM Products WHERE Category =: category";
			Query query = session.createQuery(hql);
			query.setParameter("category",category);
			products = (Products) query.getSingleResult();
			return products;

		} catch (Exception e) {
			throw e;
		}
	}
	public boolean checkSubcategorybyname(String name) {
		Subcategory subcategory;
		try (Session session = sessionFactory.openSession()) {
		String hql = "FROM Subcategory WHERE Name =: name";
		Query query = session.createQuery(hql);
		query.setParameter("name",name);
		subcategory = (Subcategory) query.getSingleResult();
			if(subcategory==null) {
				return false;
			}
			else {
				return true;
			}
		} catch (Exception e) {
			throw e;
		}
	}
	public Subcategory getSubcategorybyname(String name) {
		Subcategory subcategory;
		try (Session session = sessionFactory.openSession()) {
		String hql = "FROM Subcategory WHERE Name =: name";
		Query query = session.createQuery(hql);
		query.setParameter("name",name);
		subcategory = (Subcategory) query.getSingleResult();
			return subcategory;
		} catch (Exception e) {
			throw e;
		}
	}
	
	public Product_subcategory getPsubByproductid(Integer pid) {
		Product_subcategory product_subcategory;
		try (Session session = sessionFactory.openSession()) {
		String hql = "FROM Product_subcategory WHERE Products =: pid";
		Query query = session.createQuery(hql);
		query.setParameter("pid",pid);
		product_subcategory = (Product_subcategory) query.getSingleResult();
			return product_subcategory;
		} catch (Exception e) {
			throw e;
		}
	}

	public Products productBystring(String search) {
		// TODO Auto-generated method stub
		Products products;
		try (Session session = sessionFactory.openSession()) {
			String hql = "FROM Products WHERE Category =: search or Name=: search or subactegory:= search";
			Query query = session.createQuery(hql);
			if(query.setParameter("category",search)==null) {
				if(query.setParameter("name",search)==null) {
					//query.setParameter("",search)==null)
				}
			}
			products = (Products) query.getSingleResult();
			return products;

		} catch (Exception e) {
			throw e;
		}
	}
}
