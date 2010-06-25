package zenjiro.client;

import com.google.gwt.widgetideas.graphics.client.Color;

/**
 * ユーティリティクラス
 */
public class Util {
	/**
	 * 描画を更新します。
	 * @param canvas 描画対象
	 * @param scale 倍率
	 * @param data データ
	 */
	static void update(final Canvas canvas, final int scale, final int[][] data) {
		canvas.setPixelSize(25 * scale + 1, 8 * scale + 1);
		canvas.setCoordSize(25 * scale + 1, 8 * scale + 1);
		canvas.setFont(scale * .5 + "pt sans-serif");
		for (int i = 0; i < data.length; i++) {
			for (int j = 0; j < data[i].length; j++) {
				switch (data[i][j]) {
				case 0:
					canvas.setFillStyle(new Color("#aaffaa"));
					break;
				case 1:
					canvas.setFillStyle(new Color("#ffffaa"));
					break;
				case 2:
					canvas.setFillStyle(new Color("#ffaaaa"));
					break;
				default:
					canvas.setFillStyle(new Color("#aaaaaa"));
				}
				canvas.fillRect((j + 1) * scale, (i + 1) * scale, scale, scale);
			}
		}
		canvas.beginPath();
		canvas.setFillStyle(Color.BLACK);
		canvas.setStrokeStyle(Color.GREY);
		for (int i = 0; i < 9; i++) {
			canvas.moveTo(0, i * scale + .5);
			canvas.lineTo(canvas.getCoordWidth(), i * scale + .5);
		}
		for (int i = 0; i < 26; i++) {
			canvas.moveTo(i * scale + .5, 0);
			canvas.lineTo(i * scale + .5, canvas.getCoordHeight());
			canvas.fillText(Integer.toString(i), (i + 1) * scale + scale * .15, scale * .75);
		}
		final String[] days = new String[] { "日", "月", "火", "水", "木", "金", "土" };
		for (int i = 0; i < 7; i++) {
			canvas.fillText(days[i], scale * .15, (i + 1) * scale + scale * .75);
		}
		canvas.stroke();
	}
}
