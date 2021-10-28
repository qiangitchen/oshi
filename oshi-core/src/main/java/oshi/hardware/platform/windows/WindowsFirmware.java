/**
 * MIT License
 *
 * Copyright (c) 2010 - 2020 The OSHI Project Contributors:
 * https://github.com/oshi/oshi/graphs/contributors
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package oshi.hardware.platform.windows;

import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiQuery; // NOSONAR squid:S1191
import com.sun.jna.platform.win32.COM.WbemcliUtil.WmiResult;

import oshi.hardware.common.AbstractFirmware;
import oshi.util.platform.windows.WmiQueryHandler;
import oshi.util.platform.windows.WmiUtil;

/**
 * Firmware data obtained from WMI
 *
 * @author SchiTho1 [at] Securiton AG
 */
final class WindowsFirmware extends AbstractFirmware {

    private static final long serialVersionUID = 1L;

    enum BiosProperty {
        MANUFACTURER, NAME, DESCRIPTION, VERSION, RELEASEDATE;
    }

    WindowsFirmware() {
        init();
    }

    private void init() {
        WmiQuery<BiosProperty> biosQuery = new WmiQuery<BiosProperty>("Win32_BIOS where PrimaryBIOS=true",
                BiosProperty.class);

        WmiResult<BiosProperty> win32BIOS = WmiQueryHandler.createInstance().queryWMI(biosQuery);
        if (win32BIOS.getResultCount() > 0) {
            setManufacturer(WmiUtil.getString(win32BIOS, BiosProperty.MANUFACTURER, 0));
            setName(WmiUtil.getString(win32BIOS, BiosProperty.NAME, 0));
            setDescription(WmiUtil.getString(win32BIOS, BiosProperty.DESCRIPTION, 0));
            setVersion(WmiUtil.getString(win32BIOS, BiosProperty.VERSION, 0));
            setReleaseDate(WmiUtil.getDateString(win32BIOS, BiosProperty.RELEASEDATE, 0));
        }
    }
}
