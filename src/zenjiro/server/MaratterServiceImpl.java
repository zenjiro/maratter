package zenjiro.server;

import java.io.IOException;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import zenjiro.client.MaratterService;

import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.EntityNotFoundException;
import com.google.appengine.api.datastore.KeyFactory;
import com.google.gwt.user.server.rpc.RemoteServiceServlet;

/**
 * サーバサイドの実装
 */
@SuppressWarnings("serial")
public class MaratterServiceImpl extends RemoteServiceServlet implements MaratterService {
	@Override
	public boolean isAuthrized() {
		// TODO セッションが残り続けるので何とかする。
		return getThreadLocalRequest().getSession().getAttribute("twitter") != null;
	}

	@Override
	public String getScreenName() throws IOException {
		try {
			// TODO セッションが残り続けるので何とかする。
			final Twitter twitter = (Twitter) getThreadLocalRequest().getSession().getAttribute(
					"twitter");
			final String name = twitter.getScreenName();
			final String url = twitter.showUser(twitter.getId()).getProfileImageURL().toString();
			return "<img src=\"" + url + "\"/> " + name;
		} catch (final IllegalStateException e) {
			throw new IOException(e);
		} catch (final TwitterException e) {
			throw new IOException(e);
		}
	}

	@Override
	public int getId() throws IOException {
		try {
			// TODO セッションが残り続けるので何とかする。
			return ((Twitter) getThreadLocalRequest().getSession().getAttribute("twitter")).getId();
		} catch (final IllegalStateException e) {
			throw new IOException(e);
		} catch (final TwitterException e) {
			throw new IOException(e);
		}
	}

	@Override
	public void saveSettings(final String address, final boolean isEnabeld) throws IOException {
		try {
			// TODO セッションが残り続けるので何とかする。
			final Entity entity = new Entity(KeyFactory.createKey("settings",
					((Twitter) getThreadLocalRequest().getSession().getAttribute("twitter"))
							.getId()));
			entity.setProperty("mail address", address);
			entity.setProperty("enabled", isEnabeld);
			DatastoreServiceFactory.getDatastoreService().put(entity);
		} catch (final IllegalStateException e) {
			throw new IOException(e);
		} catch (final TwitterException e) {
			throw new IOException(e);
		}
	}

	@Override
	public String getMailAddress() throws IOException {
		try {
			// FIXME isEnabled()と統合するべき。
			return (String) DatastoreServiceFactory
					.getDatastoreService()
					.get(KeyFactory.createKey("settings", ((Twitter) getThreadLocalRequest()
							.getSession().getAttribute("twitter")).getId()))
					.getProperty("mail address");
		} catch (final IllegalStateException e) {
			throw new IOException(e);
		} catch (final TwitterException e) {
			throw new IOException(e);
		} catch (final EntityNotFoundException e) {
			return null;
		}
	}

	@Override
	public Boolean isEnabled() throws IOException {
		try {
			// FIXME getMailAddress()と統合するべき。
			// TODO セッションが残り続けるので何とかする。
			return (Boolean) DatastoreServiceFactory
					.getDatastoreService()
					.get(KeyFactory.createKey("settings", ((Twitter) getThreadLocalRequest()
							.getSession().getAttribute("twitter")).getId())).getProperty("enabled");
		} catch (final IllegalStateException e) {
			throw new IOException(e);
		} catch (final TwitterException e) {
			throw new IOException(e);
		} catch (final EntityNotFoundException e) {
			return null;
		}
	}
}
