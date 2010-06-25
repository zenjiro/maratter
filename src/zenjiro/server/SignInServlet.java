package zenjiro.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.http.RequestToken;
import zenjiro.shared.Const;

/**
 * サインイン処理を行うサーブレット
 */
public class SignInServlet extends HttpServlet {
	@Override
	protected void doGet(final HttpServletRequest request, final HttpServletResponse response)
			throws ServletException, IOException {
		final Twitter twitter = new TwitterFactory().getOAuthAuthorizedInstance(Const.CONSUMER_KEY,
				Const.CONSUMER_SECRET);
		request.getSession().setAttribute("twitter", twitter);
		try {
			final RequestToken requestToken = twitter.getOAuthRequestToken(request.getRequestURI()
					.replaceFirst("/[^/]+$", "/callbackservlet"));
			request.getSession().setAttribute("requestToken", requestToken);
			response.sendRedirect(requestToken.getAuthenticationURL());
		} catch (final TwitterException e) {
			throw new ServletException(e);
		}
	}
}
