/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.circulous.xpert.event.managedbean;

import com.circulous.xpert.jpa.entities.CustomerInfo;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.Validator;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.ValidatorException;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;

/**
 * 
 * @author SANJANA
 */
@FacesValidator("emailValidator")
public class EmailValidator implements Validator {

	// private CustomerInfo customerInfo;
	@Override
	public void validate(FacesContext fc, UIComponent uic, Object o) throws ValidatorException {
		if (o == null || o.equals("")) {
			return; // Let required="true" handle.
		}

		String username = (String) o;
		List aList = null;
		List aList1 = null;
		EntityManagerFactory emf = Persistence.createEntityManagerFactory("ROOT");
		EntityManager em = emf.createEntityManager();
		// HttpServletRequest request = (HttpServletRequest)
		// FacesContext.getCurrentInstance().getExternalContext().getRequest();

		try {
			Query query = em.createNamedQuery("CustomerInfo.findByEmail");
			// Query query =
			// em.createNativeQuery("select email from  customer_info union select emailaddr from eventxpert_users "
			// );
			query.setParameter("email", username);
			aList = query.getResultList();

			Query query1 = em.createNamedQuery("EventxpertUser.findByEmailaddr");
			// Query query =
			// em.createNativeQuery("select email from  customer_info union select emailaddr from eventxpert_users "
			// );
			query1.setParameter("emailaddr", username);
			aList1 = query1.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (aList.size() > 0 || aList1.size() > 0) {
			throw new ValidatorException(new FacesMessage(FacesMessage.SEVERITY_ERROR,
					"Email is already registered. Please try to login", null));
		}
	}

}
