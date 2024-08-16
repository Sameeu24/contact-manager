package com.ust;

import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.*;

class ContactRepositoryTest {

    ContactRepository repository;
    Contact contact1, contact2;

    @BeforeEach
    public void setUp(){
        repository = new ContactRepository();
        contact1 = new Contact();
        contact2 = new Contact();
    }

    @AfterEach
    public void tearDown(){
        repository = null;
        contact1 = null;
        contact2 = null;
    }

    @Test
    @DisplayName("Test generateId")
    public void testGenerateId(){
        long id = repository.generateId();
        assertEquals(1, id, "First id should be 1");
    }

    @Test
    @DisplayName("Create a new contact")
    public void saveNewContact() {
        var contact = repository.save("Sam", "1234567890");
        assertEquals("Sam", contact.getName(), "Name should be Ashish");
        assertTrue( contact.getPhoneNumbers().contains("1234567890"),
                "Phone number should be 1234567890");
    }

    @Test
    @DisplayName("Update an existing contact with a new phone number")
    public void updateExistingContact() {
        repository.save("Sam", "1234567890");
        var updatedContact = repository.save("Sam", "0987654321");

        // Check if the name is updated
        assertEquals("Sam", updatedContact.getName(),
                "Name should be Sam");

        // Check if the phone there are 2 phone numbers in the contact
        assertEquals(2, updatedContact.getPhoneNumbers().size(),
                "Phone numbers should be 2");

        // Check if both contact numbers are present
        assertTrue( updatedContact.getPhoneNumbers().contains("1234567890"),
                "Phone number should be 1234567890");
        assertTrue( updatedContact.getPhoneNumbers().contains("0987654321"),
                "Phone number should contain 0987654321");
    }

    @Nested
    class FindContact{
        @Test
        @DisplayName("Find contact by id")
        public void findContactById() throws ContactNotFoundException {
            var contact = repository.save("Sam", "1234567890");
            var foundContact = repository.findById(contact.getId());
            assertEquals(contact, foundContact, "Contact should be found");
        }

        @Test
        @DisplayName("Find contact by id throws exception")
        public void findContactByIdThrowsException() {
            var ex = assertThrows(ContactNotFoundException.class, () -> {
                repository.findById(1);
            });
            assertEquals("Contact with id 1 not found", ex.getMessage(),
                    "Exception message should be 'Contact not found'");
        }

        @Test
        @DisplayName("Find contact by phone number")
        public void findContactByPhoneNumber() throws ContactNotFoundException {
            var contact = repository.save("Sam", "1234567890");
            var foundContact = repository.findByPhoneNumber("1234567890");
            assertEquals(contact, foundContact, "Contact should be found");
        }

        @Test
        @DisplayName("Find contact by phone number throws exception")
        public void findContactByPhoneNumberThrowsException() {
            var ex = assertThrows(ContactNotFoundException.class, () -> {
                repository.findByPhoneNumber("1234567890");
            });
            assertEquals("Contact not found", ex.getMessage(),
                    "Exception message should be 'Contact not found'");
        }

        @Test
        @DisplayName("Find contact by name")
        public void findContactByName() throws ContactNotFoundException {
            var contact = repository.save("Ashish", "1234567890");
            var foundContact = repository.findByName("Ashish");
            assertEquals(contact, foundContact, "Contact should be found");
        }

        @Test
        @DisplayName("Find contact by name throws exception")
        public void findContactByNameThrowsException() {
            var ex = assertThrows(ContactNotFoundException.class, () -> {
                repository.findByName("Ashish");
            });
            assertEquals("Contact not found", ex.getMessage(),
                    "Exception message should be 'Contact not found'");
        }


    }

}