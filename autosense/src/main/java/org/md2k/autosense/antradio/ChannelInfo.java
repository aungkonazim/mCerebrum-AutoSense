/*
 * Copyright 2012 Dynastream Innovations Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package org.md2k.autosense.antradio;

import com.dsi.ant.message.fromant.DataMessage;

import org.md2k.autosense.devices.AutoSensePlatform;
import org.md2k.datakitapi.source.platform.PlatformType;
import org.md2k.utilities.Report.Log;

/**
 * Copyright (c) 2015, The University of Memphis, MD2K Center
 * - Syed Monowar Hossain <monowar.hossain@gmail.com>
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * * Redistributions of source code must retain the above copyright notice, this
 * list of conditions and the following disclaimer.
 *
 * * Redistributions in binary form must reproduce the above copyright notice,
 * this list of conditions and the following disclaimer in the documentation
 * and/or other materials provided with the distribution.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

public class ChannelInfo
{
    private static final String TAG = ChannelInfo.class.getSimpleName();
    public final int DEVICE_NUMBER;
    public final int CHANNEL_PROOF_DEVICE_TYPE;
    public final int CHANNEL_PROOF_TRANSMISSION_TYPE;
    public final AutoSensePlatform autoSensePlatform;

    // Set to 0 (wildcard) to find all devices.
    public final int WILDCARD_SEARCH_DEVICE_NUMBER;

    public final int CHANNEL_PROOF_PERIOD;
    public final int CHANNEL_PROOF_FREQUENCY;
    public int status = 0;

    public byte[] broadcastData = new byte[DataMessage.LENGTH_STANDARD_PAYLOAD];
    
    public boolean error;
    private String mErrorMessage;
    
    public ChannelInfo(AutoSensePlatform autoSensePlatform)
    {
        Log.d(TAG, "platformType=" + autoSensePlatform.getPlatformType() + " platformId=" + autoSensePlatform.getPlatformId() + " dataSourceType=" + autoSensePlatform.getAutoSenseDataSource().getDataSourceType());
        this.autoSensePlatform=autoSensePlatform;
        if(PlatformType.AUTOSENSE_CHEST.equals(autoSensePlatform.getPlatformType())){
            CHANNEL_PROOF_DEVICE_TYPE = (byte)0x01;
            CHANNEL_PROOF_TRANSMISSION_TYPE = 0;
            WILDCARD_SEARCH_DEVICE_NUMBER = 0;
            CHANNEL_PROOF_PERIOD = 0x04EC;//8070;
            CHANNEL_PROOF_FREQUENCY = 0x50;
            if(autoSensePlatform.getPlatformId()==null || autoSensePlatform.getPlatformId().equals(""))
                DEVICE_NUMBER=0;
            else {
                DEVICE_NUMBER=Integer.parseInt(autoSensePlatform.getPlatformId(),16);
            }
        }else{
            CHANNEL_PROOF_DEVICE_TYPE = (byte)0x03;
            CHANNEL_PROOF_TRANSMISSION_TYPE = 0;
            WILDCARD_SEARCH_DEVICE_NUMBER = 0;
            CHANNEL_PROOF_PERIOD = 1638;
            CHANNEL_PROOF_FREQUENCY = 0x50;
            if(autoSensePlatform.getPlatformId()==null || autoSensePlatform.getPlatformId().equals(""))
                DEVICE_NUMBER=0;
            else {
                DEVICE_NUMBER = Integer.parseInt(autoSensePlatform.getPlatformId(), 16);
                Log.d(TAG, "DEVICENUMBER: " + DEVICE_NUMBER);
            }
        }
        error = false;
        mErrorMessage = null;
    }
    
    public void die(String errorMessage)
    {
        error = true;
        Log.e(TAG, "platformType=" + autoSensePlatform.getPlatformType() + " platformId=" + autoSensePlatform.getPlatformId() + " error=" + errorMessage);
        mErrorMessage = errorMessage;
    }
    
    public String getErrorString()
    {
        Log.e(TAG, "error=" + mErrorMessage);
        return mErrorMessage;
    }
}
