package andriy.krupych.prayerbook;

import java.lang.reflect.Field;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewConfiguration;
import android.webkit.WebView;

public class MainActivity extends Activity {
	
	private WebView mWebView;
	private Menu mMenu;

	@SuppressLint("SetJavaScriptEnabled")
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mWebView = new WebView(this);
		mWebView.loadUrl("file:///android_asset/data.html");
		mWebView.getSettings().setJavaScriptEnabled(true);
		setContentView(mWebView);
		prepareMenu();
	}

	private void prepareMenu() {
		try {
			ViewConfiguration config = ViewConfiguration.get(this);
			Field menuKeyField = ViewConfiguration.class
					.getDeclaredField("sHasPermanentMenuKey");
			if (menuKeyField != null) {
				menuKeyField.setAccessible(true);
				menuKeyField.setBoolean(config, false);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		getMenuInflater().inflate(R.menu.main, menu);
		mMenu = menu;
		return true;
	}
	
	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		if (item.getTitle().equals(getString(R.string.scroll_up))) {
			mWebView.scrollTo(0, 0);
		} else if (item.getTitle().equals(getString(R.string.increase_font_size))) {
			mWebView.loadUrl("javascript:resize(0.1)");
		} else if (item.getTitle().equals(getString(R.string.decrease_font_size))) {
			mWebView.loadUrl("javascript:resize(-0.1)");
		} else if (item.getTitle().equals(getString(R.string.night_mode))) {
			mWebView.loadUrl("javascript:setMode(\'night\')");
			item.setTitle(R.string.day_mode);
			mMenu.findItem(R.id.action_mode).setTitle(R.string.day_mode);
		} else if (item.getTitle().equals(getString(R.string.day_mode))) {
			mWebView.loadUrl("javascript:setMode(\'day\')");
			item.setTitle(R.string.night_mode);
			mMenu.findItem(R.id.action_mode).setTitle(R.string.night_mode);
		}
		MenuItem shortcut = mMenu.findItem(R.id.action_shortcut);
		shortcut.setTitle(item.getTitle());
		shortcut.setIcon(item.getIcon());
		return false;
	}

}
