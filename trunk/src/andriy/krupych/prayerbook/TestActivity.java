package andriy.krupych.prayerbook;

import android.app.Activity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;

public class TestActivity extends Activity {
	
	private static final String KEY_URL = "KEY_URL";
	
	private String mUrl = "file:///android_asset/data.html";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		WebView view = new WebView(this);
		view.setWebViewClient(new WebViewClient() {
			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return true;
			}
			@Override
			public void onPageFinished(WebView view, String url) {
				mUrl = url;
				Toast.makeText(TestActivity.this, url, Toast.LENGTH_SHORT).show();
			}
		});
		setContentView(view);
		if (savedInstanceState != null)
			mUrl = savedInstanceState.getString(KEY_URL);
		view.loadUrl(mUrl);
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		outState.putString(KEY_URL, mUrl);
		super.onSaveInstanceState(outState);
	}

}
