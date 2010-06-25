package zenjiro.client;

import com.google.gwt.user.client.rpc.AsyncCallback;

/**
 * The async counterpart of <code>GreetingService</code>.
 */
public interface MaratterServiceAsync {
	/**
	 * Twitter認証済みかどうか
	 * @param callback コールバック
	 */
	void isAuthrized(AsyncCallback<Boolean> callback);

	/**
	 * 表示名
	 * @param callback コールバック
	 */
	void getScreenName(AsyncCallback<String> callback);

	/**
	 * ユーザID
	 * @param callback コールバック
	 */
	void getId(AsyncCallback<Integer> callback);

	/**
	 * @param mailAddress メールアドレス
	 * @param isEnabeld 有効かどうか
	 * @param callback コールバック
	 */
	void saveSettings(String mailAddress, boolean isEnabeld, AsyncCallback<Void> callback);

	/**
	 * メールアドレスを読み込みます。
	 * @param callback コールバック
	 */
	void getMailAddress(AsyncCallback<String> callback);

	/**
	 * 有効かどうか
	 * @param callback コールバック
	 */
	void isEnabled(AsyncCallback<Boolean> callback);
}
