/* 
 * Copyright (C) 2014 Peter Cai
 *
 * This file is part of BlackLight
 *
 * BlackLight is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * BlackLight is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with BlackLight.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.finderlo.weixzz.ui.Login;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.MenuItem;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Toast;


import com.finderlo.weixzz.R;
import com.finderlo.weixzz.Utility.Util;
import com.finderlo.weixzz.base.BaseActivity;
import com.finderlo.weixzz.dao.LoginDao;
import com.finderlo.weixzz.dao.UrlConstants;
import com.finderlo.weixzz.ui.Mainview.MainViewActivity;


import static com.finderlo.weixzz.BuildConfig.DEBUG;


/* BlackMagic Login Activity */
public class WebLoginActivity extends BaseActivity {
	private static final String TAG = WebLoginActivity.class.getSimpleName();

	public static final String WEICO_SCOPE = "email,direct_messages_read,direct_messages_write," +
			"friendships_groups_read,friendships_groups_write,statuses_to_me_read,follow_app_official_microblog,invitation_write";
	public static final String WEICO_CLIENT_ID = "211160679";
	public static final String WEICO_REDIRCT_URL = "http://oauth.weico.cc";
	public static final String WEICO_APP_KEY = "1e6e33db08f9192306c4afa0a61ad56c";
	public static final String WEICO_PACKNAME = "com.eico.weico";

	WebView webView;

	Toolbar toolbar;
	
	private MenuItem mMenuItem;
	

	private String mAppId;
	private String mAppSecret;


	private boolean isDoingLogin=false;

	private LoginDao mLogin;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
//		mLayout = R.layout.login;
		super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_web_login);
        webView = (WebView) findViewById(R.id.wb_login);
		toolbar = (Toolbar) findViewById(R.id.toolbar);

		// Create login instance
		mLogin = LoginDao.getInstance(this);

		setSupportActionBar(toolbar);

		// Login page
		WebSettings settings = webView.getSettings();
		settings.setJavaScriptEnabled(true);
		settings.setSaveFormData(false);
		settings.setSavePassword(false);
		settings.setCacheMode(WebSettings.LOAD_NO_CACHE);

		webView.setWebViewClient(new MyWebViewClient());

		webView.loadUrl(getOauthLoginPage());




	}



	private class MyWebViewClient extends WebViewClient {

		@Override
		public boolean shouldOverrideUrlLoading(WebView view, String url) {

			if (isUrlRedirected(url)) {
				view.stopLoading();
				Log.d(TAG, "shouldOverrideUrlLoading...");
				handleRedirectedUrl(url);
			} else {
				view.loadUrl(url);
			}

			return true;
		}

		@Override
		public void onPageStarted(WebView view, String url, Bitmap favicon) {
			if (!url.equals("about:blank") && isUrlRedirected(url)) {
				view.stopLoading();
				Log.d(TAG, "onPageStarted...");
				handleRedirectedUrl(url);
				return;
			}
			super.onPageStarted(view, url, favicon);
		}

	}






	private void handleRedirectedUrl(String url) {
		Log.d(TAG, "handleRedirectedUrl...");

		if (!url.contains("error")) {
			int tokenIndex = url.indexOf("access_token=");
			int expiresIndex = url.indexOf("expires_in=");
			String token = url.substring(tokenIndex + 13, url.indexOf("&", tokenIndex));
			String expiresIn = url.substring(expiresIndex + 11, url.indexOf("&", expiresIndex));

			if (DEBUG) {
				Log.d(TAG, "url = " + url);
				Log.d(TAG, "token = " + token);
				Log.d(TAG, "expires_in = " + expiresIn);
			}

			if (!isDoingLogin)
				new LoginTask().execute(token, expiresIn);


		} else {
			showLoginFail();
		}
	}


	private class LoginTask extends AsyncTask<String, Void, Long>
	{
		private ProgressDialog progDialog;

		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			isDoingLogin=true;
			progDialog = new ProgressDialog(WebLoginActivity.this);
			progDialog.setMessage("正在载入数据");
			progDialog.setCancelable(false);
			progDialog.show();
		}

		@Override
		protected Long doInBackground(String... params) {
			if (DEBUG) {
				Log.d(TAG, "doInBackground...");
			}
				mLogin.login(params[0], params[1]);
				return mLogin.getExpireDate();

		}

		@Override
		protected void onPostExecute(Long result) {
			super.onPostExecute(result);
			progDialog.dismiss();
			isDoingLogin=false;

			if ( mLogin.getAccessToken() != null) {
				if (DEBUG) {
					Log.d(TAG, "Access Token:" + mLogin.getAccessToken());
					Log.d(TAG, "Expires in:" + mLogin.getExpireDate());
				}
				mLogin.cache();

			} else if (mLogin.getAccessToken() == null) {
				showLoginFail();
				return;
			}
			String msg = String.format("授权%d天有效", Util.expireTimeInDays(result));
			// Expire date
			Toast.makeText(WebLoginActivity.this, msg, Toast.LENGTH_LONG).show();
			Intent i = new Intent(WebLoginActivity.this, MainViewActivity.class);
			startActivity(i);
			finish();
		}

	}

	private void showLoginFail() {
		// Wrong username or password
		new AlertDialog.Builder(WebLoginActivity.this)
				.setMessage("失败")
				.setCancelable(true)
				.create()
				.show();
	}


	public static String getOauthLoginPage() {
		return UrlConstants.OAUTH2_ACCESS_AUTHORIZE + "?" + "client_id=" + WEICO_CLIENT_ID
				+ "&response_type=token&redirect_uri=" + WEICO_REDIRCT_URL
				+ "&key_hash=" + WEICO_APP_KEY + (TextUtils.isEmpty(WEICO_PACKNAME) ? "" : "&packagename=" + WEICO_PACKNAME)
				+ "&display=mobile" + "&scope=" + WEICO_SCOPE;
	}

	public static boolean isUrlRedirected(String url) {
		return url.startsWith(WEICO_REDIRCT_URL);
	}

}
