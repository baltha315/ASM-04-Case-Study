/**
 * This Business Object class is used to for Homeowner Information
 * 
 * @author Cognizant
 * @contact Cognizant
 * @version 1.0
 */
package com.cts.insurance.homequote.bo;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import com.cts.insurance.homequote.dao.PolicyDAO;
import com.cts.insurance.homequote.exception.HomequoteBusinessException;
import com.cts.insurance.homequote.exception.HomequoteSystemException;
import com.cts.insurance.homequote.model.Policy;
import com.cts.insurance.homequote.util.HomeInsuranceConstants;

public class PolicyBO {
	/**
	 * @param quoteId
	 * @param policyEffectiveDate
	 * @return
	 */
	public Policy savePolicy(final int quoteId, final String policyEffDate, final int term) throws HomequoteBusinessException{
		final PolicyDAO poilcyDAO = new PolicyDAO();
		try
		{	
			final Policy policy = new Policy();		
			policy.setQuoteId(quoteId);
			policy.setPolicyEffDate(policyEffDate);
			policy.setPolicyEndDate(getDateAfterOneYear(policyEffDate));
			policy.setPolicyTerm(term);
			policy.setPolicyKey(quoteId + "_" + policy.getPolicyTerm());
			policy.setPolicyStatus(HomeInsuranceConstants.STATUS_ACTIVE);
			poilcyDAO.savePolicy(policy);
			return policy;
		}
		catch(ParseException e)
		{
			throw new HomequoteBusinessException(e.getMessage());
		}
		catch(HomequoteSystemException e)
		{
			throw new HomequoteBusinessException(e.getMessage());
		}
	}

	/**
	 * @param userName
	 * @return
	 * @throws HomequoteBusinessException
	 * @throws HomequoteSystemException 
	 */
	public List<Policy> getPolicies(final String userName) throws HomequoteBusinessException{
		
		List<Policy> policyList = new ArrayList<Policy>();
		final PolicyDAO policyDAO = new PolicyDAO();
		try {
			policyList = policyDAO.getPolicies(userName);
			} catch (HomequoteSystemException e) {
				throw new HomequoteBusinessException(e.getMessage());	
		}
		System.out.println(policyList); //Test
		return policyList;
	}
	
	/**
	 * @param policyKey
	 * @return
	 * @throws HomequoteBusinessException
	 * @throws HomequoteSystemException 
	 */
	public Policy cancelPolicy(final String policyKey) throws HomequoteBusinessException{
		
		Policy policy = new Policy();
		final PolicyDAO policyDAO = new PolicyDAO();
		try {
			policy = policyDAO.cancelPolicy(policyKey);
			} catch (HomequoteSystemException e) {
				throw new HomequoteBusinessException(e.getMessage());	
		}
		return policy; 
	}
	
	/**
	 * @param policyKey
	 * @return
	 * @throws HomequoteBusinessException
	 * @throws HomequoteSystemException 
	 */
	public Policy renewPolicy(final String policyKey) throws HomequoteBusinessException{
		
		Policy policy = new Policy();
		final PolicyDAO policyDAO = new PolicyDAO();
		try {
			policy = policyDAO.renewPolicy(policyKey);
			} catch (HomequoteSystemException e) {
				throw new HomequoteBusinessException(e.getMessage());	
		}
		return policy;
	}
	/**
	 * @param policyEffectiveDate
	 * @throws ParseException
	 */
	private String getDateAfterOneYear(final String policyEffDate) throws ParseException
	{
		Calendar c = Calendar.getInstance();
		Date dateDay = new SimpleDateFormat("yyyy-MM-dd").parse(policyEffDate);
		c.setTime(dateDay);
		c.add(Calendar.YEAR, 1);
		String policyAfterOneYear = (new SimpleDateFormat("yyyy-MM-dd").format(dateDay));
		return policyAfterOneYear;
	}
}
