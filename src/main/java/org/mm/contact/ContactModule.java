package org.mm.contact;

import com.google.gdata.client.contacts.ContactsService;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;

/**
 * User: Mustafa Motiwala
 * Date: May 17, 2010
 * Time: 8:03:08 PM
 */
public class ContactModule extends AbstractModule {
    @Override
    protected void configure() {
        bind(ContactsService.class).annotatedWith(Names.named("svcContacts")).toInstance(new ContactsService("Contacts Organizer"));
        bind(GoogleLoginServiceAdapter.class).toInstance(new GoogleLoginServiceAdapter());
        bind(Runnable.class).annotatedWith(Names.named("contactLoader")).to(ContactLoader.class).in(Scopes.SINGLETON);
    }
}
