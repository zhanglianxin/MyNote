/*
 * Copyright (c) 2007 NTT DATA Corporation
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package jp.terasoluna.thin.tutorial.web.common.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import jp.terasoluna.fw.util.PropertyUtil;
import jp.terasoluna.fw.web.RequestUtil;
import jp.terasoluna.fw.web.thin.AuthenticationController;
import jp.terasoluna.thin.tutorial.web.common.uvo.TutorialUVO;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 認証チェックを行う。
 * 
 */
public class TutorialAuthController implements AuthenticationController {

    /**
     * ログクラス
     */
    private static Log log =
        LogFactory.getLog(TutorialAuthController.class);

    /**
     *  認証チェックを行わないパス情報リストを取得キー。
     */
    private static final String AUTHENTICATED_NOCHECK_PATH_PREFIX
                                 = "access.control.authenticated.escape.";

    /**
     *  認証チェックを行わないパス情報リストを取得キー。
     */
    private List<String> noCheckList = null;

    /**
     * リクエストのパス情報に対して、指定されたHTTPセッションが
     * 認証済みであるかどうかを判定する。
     *
     * @param pathInfo パス情報
     * @param req HTTPリクエスト
     * 
     * @return 認証に成功すれば <code>true</code>
     */
    public boolean isAuthenticated(String pathInfo, ServletRequest req) {

        if (log.isDebugEnabled()) {
            log.debug("call isAuthenticated");
        }

        //セッションからUVOを取得する。
        HttpSession session = ((HttpServletRequest) req).getSession();
        TutorialUVO uvo = (TutorialUVO) session.getAttribute("USER_VALUE_OBJECT");

        //UVO、またはUVOに登録されているIDがnullの場合はfalseを返却する。
        if (uvo != null && uvo.getUserId() != null) {
            return true;
        }
        return false;
    }

    /**
     * パスがチェック対象か否か判定する。
     * 
     * @param req 判定対象となる <code>ServletRequest</code> インスタンス
     * 
     * @return チェック対象の場合は<code>true</code>
     */
    public boolean isCheckRequired(ServletRequest req) {

        if (log.isDebugEnabled()) {
            log.debug("call isCheckRequired()");
        }

        //パス情報を取得する。
        String pathInfo = RequestUtil.getPathInfo(req);

        if (noCheckList == null) {
            noCheckList = new ArrayList<String>();
            for (int i = 1; ; i++) {
                String path = PropertyUtil.getProperty(
                        AUTHENTICATED_NOCHECK_PATH_PREFIX + i);
                if (path == null) {
                    break;
                }
                noCheckList.add(path);
            }
        }
        
        for (String path : noCheckList) {
            if (pathInfo.startsWith(path) || "/".equals(pathInfo)) {
                return false;
            }
        }

        return true;
    }

}
