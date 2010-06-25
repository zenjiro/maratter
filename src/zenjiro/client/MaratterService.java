package zenjiro.client;

import java.io.IOException;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

/**
 * The client side stub for the RPC service.
 */
@RemoteServiceRelativePath("maratter")
public interface MaratterService extends RemoteService {
	/**
	 * @return Twitter認証済みかどうか
	 */
	boolean isAuthrized();
	
	/**
	 * @return 表示名
	 * @throws IOException 入出力例外
	 */
	String getScreenName() throws IOException;
	
	/**
	 * @return ユーザID
	 * @throws IOException 入出力例外
	 */
	int getId()  throws IOException;
	
	/**
	 * @param mailAddress メールアドレス
	 * @param isEnabeld 有効かどうか
	 * @throws IOException 入出力例外
	 */
	void saveSettings(String mailAddress, boolean isEnabeld) throws IOException;
	
	/**
	 * @return メールアドレス
	 * @throws IOException 入出力例外
	 */
	String getMailAddress() throws IOException;
	
	/**
	 * @return 有効かどうか
	 * @throws IOException 入出力例外
	 */
	Boolean isEnabled() throws IOException;
}
