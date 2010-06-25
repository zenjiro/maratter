package zenjiro.client;

import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.dom.client.MouseDownHandler;
import com.google.gwt.event.dom.client.MouseMoveEvent;
import com.google.gwt.event.dom.client.MouseMoveHandler;
import com.google.gwt.event.dom.client.MouseUpEvent;
import com.google.gwt.event.dom.client.MouseUpHandler;
import com.google.gwt.event.dom.client.MouseWheelEvent;
import com.google.gwt.event.dom.client.MouseWheelHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.widgetideas.graphics.client.GWTCanvas;

/**
 * マウスイベントを拾うGWTCanvas
 * http://code.google.com/p/google-web-toolkit-incubator/wiki/GWTCanvas
 */
public class Canvas extends GWTCanvas {
	/**
	 * コンストラクタ
	 */
	public Canvas() {
		super();
	}

	/**
	 * コンストラクタ
	 * @param coordX 座標系の幅
	 * @param coordY 座標系の高さ
	 */
	public Canvas(final int coordX, final int coordY) {
		super(coordX, coordY);
	}

	/**
	 * コンストラクタ
	 * @param coordX 座標系の幅
	 * @param coordY 座標系の高さ
	 * @param pixelX 実際の幅
	 * @param pixelY 実際の高さ
	 */
	public Canvas(final int coordX, final int coordY, final int pixelX, final int pixelY) {
		super(coordX, coordY, pixelX, pixelY);
	}

	/**
	 * @param handler マウスボタンが押されたときに実行されるハンドラ
	 * @return ハンドラ登録
	 */
	public HandlerRegistration addMouseHandler(final MouseDownHandler handler) {
		return addDomHandler(handler, MouseDownEvent.getType());
	}

	/**
	 * @param handler マウスボタンが離されたときに実行されるハンドラ
	 * @return ハンドラ登録
	 */
	public HandlerRegistration addMouseHandler(final MouseUpHandler handler) {
		return addDomHandler(handler, MouseUpEvent.getType());
	}

	/**
	 * @param handler マウスホイールが操作されたときに実行されるハンドラ
	 * @return ハンドラ登録
	 */
	public HandlerRegistration addMouseHandler(final MouseWheelHandler handler) {
		return addDomHandler(handler, MouseWheelEvent.getType());
	}

	/**
	 * @param handler マウスボタンが動いたときに実行されるハンドラ
	 * @return ハンドラ登録
	 */
	public HandlerRegistration addMouseHandler(final MouseMoveHandler handler) {
		return addDomHandler(handler, MouseMoveEvent.getType());
	}

	/**
	 * @param string 文字列
	 * @param x x座標
	 * @param y y座標
	 */
	public void fillText(final String string, final double x, final double y) {
		canvasFillText(this, string, x, y);
	}

	/**
	 * @param font フォント
	 */
	public void setFont(final String font) {
		canvasSetFont(this, font);
	}

	/**
	 * http://d.hatena.ne.jp/nowokay/20091206#1260123377
	 * @param c 描画対象
	 * @param str 文字列
	 * @param x x座標
	 * @param y ｙ座標
	 */
	private static native void canvasFillText(GWTCanvas c, String str, double x, double y)
	/*-{
		var impl = c.@com.google.gwt.widgetideas.graphics.client.GWTCanvas::impl;
		(impl.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).fillText(str, x, y);
	}-*/;

	/**
	 * http://d.hatena.ne.jp/nowokay/20091206#1260123377
	 * @param c 描画対象
	 * @param f フォント
	 */
	static native void canvasSetFont(GWTCanvas c, String f)
	/*-{
		var impl = c.@com.google.gwt.widgetideas.graphics.client.GWTCanvas::impl;
		(impl.@com.google.gwt.widgetideas.graphics.client.impl.GWTCanvasImplDefault::canvasContext).font = f;
	}-*/;

}
