package andriy.krupych.prayerbook;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.webkit.WebView;
import android.webkit.WebViewClient;

public class MainActivity extends Activity {
	
	private static final String KEY_SCROLL = "KEY_SCROLL";
	
	private WebView mWebView;

    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        mWebView = (WebView) findViewById(R.id.web_view);
        if (savedInstanceState == null) {
        	mWebView.loadUrl("file:///android_asset/data.html");
        } else {
        	mWebView.restoreState(savedInstanceState);
        	mWebView.setWebViewClient(new WebViewClient() {
        		@Override
        		public void onPageFinished(WebView view, String url) {
        			super.onPageFinished(view, url);
        			mWebView.postDelayed(new Runnable() {
						@Override
						public void run() {
		        			mWebView.scrollTo(0, parseProgression(
		        					savedInstanceState.getFloat(KEY_SCROLL)));
						}
					}, 500);
        		}
        	});
        	mWebView.scrollTo(0, parseProgression(savedInstanceState.getFloat(KEY_SCROLL)));
        }
        
//        try {
//			Document document = Jsoup.connect("file:///android_asset/data.html").get();
//			Elements elements = document.getElementsByTag("h3");
//			document.after(elements.first());
//		} catch (IOException e) {
//			e.printStackTrace();
//		}
    }
    
    @Override
    protected void onSaveInstanceState(Bundle outState) {
    	mWebView.saveState(outState);
    	outState.putFloat(KEY_SCROLL, calculateProgression());
    	super.onSaveInstanceState(outState);
    }
    
    private float calculateProgression() {
        float positionTopView = mWebView.getTop();
        float contentHeight = mWebView.getContentHeight();
        float currentScrollPosition = mWebView.getScrollY();
        float percentWebview = (currentScrollPosition - positionTopView) / contentHeight;
        StringBuilder builder = new StringBuilder();
        builder.append(positionTopView).append(" ").append(contentHeight).append(" ")
        	.append(currentScrollPosition).append(" ").append(percentWebview);
        Log.d("qwerty", builder.toString());
        return percentWebview;
    }
    
    private int parseProgression(float progression) {
        StringBuilder builder = new StringBuilder();
        builder.append(mWebView.getTop()).append(" ")
        	.append(mWebView.getContentHeight()).append(" ").append(progression);
        Log.d("asdfg", builder.toString());
    	float webviewsize = mWebView.getContentHeight() - mWebView.getTop();
        float positionInWV = webviewsize * progression;
        return Math.round(mWebView.getTop() + positionInWV);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }
    
}
