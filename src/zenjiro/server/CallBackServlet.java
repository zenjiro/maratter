package zenjiro.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.http.AccessToken;
import twitter4j.http.RequestToken;

import com.google.appengine.api.datastore.DatastoreService;
import com.google.appengine.api.datastore.DatastoreServiceFactory;
import com.google.appengine.api.datastore.Entity;
import com.google.appengine.api.datastore.KeyFactory;

/**
 * サインイン処理が終わったときにコールバックされるサーブレット
 */
public class CallBackServlet extends HttpServlet {
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
		final RequestToken requestToken = (RequestToken) request.getSession().getAttribute(
				"requestToken");
		final String verifier = request.getParameter("oauth_verifier");
		try {
			final AccessToken accessToken = twitter.getOAuthAccessToken(requestToken, verifier);
			request.getSession().removeAttribute("requestToken");
			final Entity entity = new Entity(KeyFactory.createKey("access token",
					accessToken.getUserId()));
			entity.setProperty("token", accessToken.getToken());
			entity.setProperty("tokenSecret", accessToken.getTokenSecret());
			final DatastoreService service = DatastoreServiceFactory.getDatastoreService();
			service.put(entity);
		} catch (final TwitterException e) {
			throw new ServletException(e);
		}
		response.sendRedirect(request.getContextPath() + "/");
	}
}
