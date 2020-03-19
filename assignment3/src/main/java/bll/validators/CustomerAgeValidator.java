package bll.validators;

import model.Customer;

/**
 * @Author: Technical University of Cluj-Napoca, Romania Distributed Systems
 *          Research Laboratory, http://dsrl.coned.utcluj.ro/
 * @Since: Apr 03, 2017
 */
public class CustomerAgeValidator implements Validator<Customer> {
	private static final int MIN_AGE = 18;
	

	public void validate(Customer c) {

		if (c.getAge() < MIN_AGE ) {
			throw new IllegalArgumentException("The Customer Age limit is not respected!");
		}

	}

}
