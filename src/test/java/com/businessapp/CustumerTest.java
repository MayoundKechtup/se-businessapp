package com.businessapp;

import static org.junit.Assert.*;
import static org.hamcrest.CoreMatchers.*;

import org.hamcrest.Matcher;
import org.junit.Test;

import com.businessapp.pojos.Customer;
import com.businessapp.pojos.Customer.CustomerStatus;

public class CustumerTest {
final String empty= "";

final Customer c1 = new Customer("HAB32A", "Klaus Krum");
final Customer c3 = new Customer("HABS3A", "Habibi Baba");
	

	@Test
	public void testCustomerConstructor() {
	final Customer c2 = new Customer("JFHDFJ2", "Jochen Jung");
	assertEquals("JFHDFJ2", c2.getId());
	assertEquals("Jochen Jung", c2.getName());	
	}
	
	@Test
	public void testGetId() {
		
		final String test= "SABUE2A";
		c1.setId(test);
		assertEquals(test, c1.getId());
		c1.setId(empty);
		assertEquals(empty, c1.getId());
	//	c1.setId(null);
	//	assertNull(c1.getId());
		//assertThat(test == c1.getId(), is(true));		
		
		final Customer cId = new Customer("SUASNUN", "Paul Punter");
		 assertEquals("SUASNUN", cId.getId());
		 final Customer cIdNull= new Customer (null , "Max Munter");
		 //	 assertThat(cIdNull.getId(), not(EqualTo(null));
	}
	
	@Test
	public void testSetGetName() {
		final String testName = "Günter Gunni";
		c1.setName(testName);
		assertEquals(testName, c1.getName());
		
		//assertThat(testName == c1.getName(), is(true));
		
		c1.setName(empty);
		assertEquals(empty, c1.getName());
		c1.setName(null);
		assertNull(c1.getName());
	}
	
	@Test
	public void testSetGetStatus() {
		
		final CustomerStatus status ;
		status= CustomerStatus.ACTIVE;
		c1.setStatus(status);
		assertEquals(status, c1.getStatus());
		c1.setStatus(null);
		assertNull(c1.getStatus());
		
	}
	
	@Test
	public void testAddGetContacts(){
		c1.addContact("Peter");
		assertEquals("Peter",c1.getContacts().get(0));
		
		c3.addContact(null);
		assertNull(c3.getContacts().get(0));
		
		c3.addContact("");
		assertEquals("", c3.getContacts().get(1));
	}
	@Test 
	public void testSetGetNotes() {
		c1.setNotes("note");
		//assertEquals("note", c1.getNotes().get(0).getLog());
		
		c1.setNotes(null);
		assertNull(null, c1.getNotes());
		
		c1.setNotes("");
		assertEquals("", c1.getNotes());
		
	}
	//
	private Matcher is(boolean b) {
		// TODO Auto-generated method stub
		return null;
	}
}
