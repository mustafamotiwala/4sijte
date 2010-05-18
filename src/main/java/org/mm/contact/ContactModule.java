package org.mm.contact;

import javax.swing.Action;

import com.google.gdata.client.contacts.ContactsService;
import com.google.inject.AbstractModule;
import com.google.inject.Scopes;
import com.google.inject.name.Names;
import org.mm.action.LoginAction;

/**
 * User: Mustafa Motiwala
 * Date: May 17, 2010
 * Time: 8:03:08 PM
 */
public class ContactModule extends AbstractModule {
    @Override
    protected void configure() {
        final ContactsService service = new ContactsService("Contacts Organizer");
        bind(ContactsService.class).annotatedWith(Names.named("svcContacts")).toInstance(service);
        bind(GoogleLoginServiceAdapter.class).toInstance(new GoogleLoginServiceAdapter());
        bind(Action.class).annotatedWith(Names.named("loginAction")).to(LoginAction.class).in(Scopes.SINGLETON);
        bind(Runnable.class).annotatedWith(Names.named("contactLoader")).to(ContactLoader.class).in(Scopes.SINGLETON);
    }
}
