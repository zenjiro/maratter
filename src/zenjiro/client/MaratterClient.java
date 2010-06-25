package zenjiro.client;

import com.google.gwt.core.client.EntryPoint;
import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.RadioButton;
import com.google.gwt.user.client.ui.RootPanel;
import com.google.gwt.user.client.ui.TextBox;

/**
 * Entry point classes define <code>onModuleLoad()</code>.
 */
public class MaratterClient implements EntryPoint {

	/**
	 * サーバ通信用のオブジェクト
	 */
	final MaratterServiceAsync service = GWT.create(MaratterService.class);

	/**
	 * マウスボタンが押し下げられた行
	 */
	int mouseDownRow = -1;
	/**
	 * マウスボタンが押し下げられた列
	 */
	int mouseDownColumn = -1;

	/**
	 * モジュールがロードされたときに実行される
	 */
	@Override
	public void onModuleLoad() {
		RootPanel.get().add(new HTML("<h1>まらったー</h1>"));
		RootPanel.get().add(new Label("自分へのmentionをメールで通知します。"));
		this.service.isAuthrized(new AsyncCallback<Boolean>() {
			@Override
			public void onSuccess(final Boolean isAuthrized) {
				if (isAuthrized) {
					final int accountRow = 0;
					final int mailAddressRow = 1;
					final int toolRow = 2;
					final int canvasRow = 3;
					final int notificationRow = 4;
					final int saveRow = 5;
					final Grid grid = new Grid(saveRow + 1, 2);
					grid.setText(accountRow, 0, "Twitterアカウント");
					grid.setText(mailAddressRow, 0, "メールアドレス");
					final TextBox mailAddressTextBox = new TextBox();
					mailAddressTextBox.setWidth("15em");
					final FlowPanel toolPanel = new FlowPanel();
					final RadioButton nowRadio = new RadioButton("tool", "すぐ通知");
					nowRadio.setStyleName("nowRadio");
					toolPanel.add(nowRadio);
					final RadioButton queueRadio = new RadioButton("tool", "溜めておく");
					queueRadio.setStyleName("queueRadio");
					queueRadio.setValue(true);
					toolPanel.add(queueRadio);
					final RadioButton dropRadio = new RadioButton("tool", "捨てる");
					dropRadio.setStyleName("dropRadio");
					toolPanel.add(dropRadio);
					grid.setWidget(toolRow, 1, toolPanel);
					grid.setText(canvasRow, 0, "時間帯");
					final int scale = 28;
					final Canvas canvas = new Canvas();
					canvas.setStyleName("canvas");
					final int[][] data = new int[7][24];
					Util.update(canvas, scale, data);
					canvas.addMouseHandler(new MouseDownHandler() {
						@Override
						public void onMouseDown(final MouseDownEvent event) {
							MaratterClient.this.mouseDownRow = event.getY() / scale - 1;
							MaratterClient.this.mouseDownColumn = event.getX() / scale - 1;
						}
					});
					canvas.addMouseHandler(new MouseMoveHandler() {
						@Override
						public void onMouseMove(MouseMoveEvent event) {
							if (MaratterClient.this.mouseDownRow >= 0
									&& MaratterClient.this.mouseDownColumn >= 0) {
								int[][] temporaryData = new int[data.length][data[0].length];
								for (int i = 0; i < data.length; i++) {
									for (int j = 0; j < data[i].length; j++) {
										temporaryData[i][j] = data[i][j];
									}
								}
								int value = nowRadio.getValue() ? 0 : (queueRadio.getValue() ? 1
										: 2);
								int row = event.getY() / scale - 1;
								int column = event.getX() / scale - 1;
								for (int i = Math.min(MaratterClient.this.mouseDownRow, row); i <= Math
										.max(MaratterClient.this.mouseDownRow, row); i++) {
									for (int j = Math.min(MaratterClient.this.mouseDownColumn,
											column); j <= Math.max(
											MaratterClient.this.mouseDownColumn, column); j++) {
										temporaryData[i][j] = value;
									}
								}
								Util.update(canvas, scale, temporaryData);
							}
						}
					});
					canvas.addMouseHandler(new MouseUpHandler() {
						@Override
						public void onMouseUp(MouseUpEvent event) {
							if (MaratterClient.this.mouseDownRow >= 0
									&& MaratterClient.this.mouseDownColumn >= 0) {
								int value = nowRadio.getValue() ? 0 : (queueRadio.getValue() ? 1
										: 2);
								int row = event.getY() / scale - 1;
								int column = event.getX() / scale - 1;
								for (int i = Math.min(MaratterClient.this.mouseDownRow, row); i <= Math
										.max(MaratterClient.this.mouseDownRow, row); i++) {
									for (int j = Math.min(MaratterClient.this.mouseDownColumn,
											column); j <= Math.max(
											MaratterClient.this.mouseDownColumn, column); j++) {
										data[i][j] = value;
									}
								}
								Util.update(canvas, scale, data);
							}
							MaratterClient.this.mouseDownRow = -1;
							MaratterClient.this.mouseDownColumn = -1;
						}
					});
					grid.setWidget(canvasRow, 1, canvas);
					grid.setWidget(mailAddressRow, 1, mailAddressTextBox);
					grid.setText(notificationRow, 0, "通知");
					final CheckBox enableCheckBox = new CheckBox("通知を有効にする。");
					grid.setWidget(notificationRow, 1, enableCheckBox);
					final Button saveButton = new Button("保存");
					grid.setWidget(saveRow, 1, saveButton);
					RootPanel.get().add(grid);
					final Label label = new Label();
					RootPanel.get().add(label);
					MaratterClient.this.service.getScreenName(new AsyncCallback<String>() {
						@Override
						public void onSuccess(final String screenName) {
							grid.setHTML(0, 1, screenName);
						}

						@Override
						public void onFailure(final Throwable caught) {
							label.setText(caught.getLocalizedMessage());
						}
					});
					MaratterClient.this.service.getMailAddress(new AsyncCallback<String>() {
						@Override
						public void onSuccess(final String address) {
							mailAddressTextBox.setText(address == null ? "" : address);
						}

						@Override
						public void onFailure(final Throwable caught) {
							label.setText(caught.getLocalizedMessage());
						}
					});
					MaratterClient.this.service.isEnabled(new AsyncCallback<Boolean>() {
						@Override
						public void onSuccess(final Boolean isEnabled) {
							enableCheckBox.setValue(isEnabled == null ? true : isEnabled);
						}

						@Override
						public void onFailure(final Throwable caught) {
							label.setText(caught.getLocalizedMessage());
						}
					});
					saveButton.addClickHandler(new ClickHandler() {
						@Override
						public void onClick(final ClickEvent event) {
							MaratterClient.this.service.saveSettings(mailAddressTextBox.getText(),
									enableCheckBox.getValue(), new AsyncCallback<Void>() {
										@Override
										public void onSuccess(final Void result) {
											label.setText("設定を保存しました。");
										}

										@Override
										public void onFailure(final Throwable caught) {
											label.setText(caught.getLocalizedMessage());
										}
									});
						}
					});
				} else {
					RootPanel.get().add(new HTML("認証されていません。<a href=\"signinservlet\">認証する</a>"));
				}
			}

			@Override
			public void onFailure(final Throwable caught) {
				RootPanel.get().add(new Label(caught.getLocalizedMessage()));
			}
		});
	}
}
