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

package jp.terasoluna.thin.tutorial.web.common.uvo;

import jp.terasoluna.fw.web.UserValueObject;

/**
 * ���[�U�[�o�����[�I�u�W�F�N�g�����N���X�B
 *
 */
public class TutorialUVO extends UserValueObject {

    /**
     * �V���A���o�[�W����ID�B
     */
    private static final long serialVersionUID = 1L;

    /**
     * ���[�UID�B
     */
    private String userId = null;
    
    /**
     * �p�X���[�h
     */
    private String password = null;

    /**
     * ���[�UID��ԋp����B
     *
     * @return �ێ����郆�[�UID
     */
    public String getUserId() {
        return userId;
    }

    /**
     * ���[�UID��ݒ肷��B
     *
     * @param userId ���[�UID
     */
    public void setUserId(String userId) {
        this.userId = userId;
    }

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

}
