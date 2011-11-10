/*******************************************************************************
 * Copyright 2011 Google Inc. All Rights Reserved.
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
package com.google.gdt.eclipse.designer.ie;

import org.eclipse.swt.SWT;
import org.eclipse.swt.SWTException;
import org.eclipse.swt.browser.Browser;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Shell;


import com.google.gdt.eclipse.designer.ie.jsni.ModuleSpaceIE6;
import com.google.gdt.eclipse.designer.ie.util.Utils;
import com.google.gwt.core.ext.TreeLogger;
import com.google.gwt.dev.shell.JsValue;
import com.google.gwt.dev.shell.ModuleSpaceHost;

import org.eclipse.wb.internal.core.utils.ui.UiUtils;


/**
 * Browser shell implementation for Windows.
 * 
 * @author mitin_aa
 */
public final class BrowserShellIE6 extends com.google.gdt.eclipse.designer.hosted.tdz.BrowserShell {
	private final Shell m_shell;
	private final Browser m_browser;
	private final IEInitializer m_helper;
	////////////////////////////////////////////////////////////////////////////
	//
	// Constructor
	//
	////////////////////////////////////////////////////////////////////////////
	public BrowserShellIE6() {
		m_shell = new Shell(SWT.SHELL_TRIM | SWT.APPLICATION_MODAL);
		m_shell.setLayout(new FillLayout());
		m_shell.setLocation(-10000, -10000);
		// prevent close, hide instead
		m_shell.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent e) {
				e.doit = false;
				Shell shell = (Shell) e.widget;
				shell.setMinimized(false);
				shell.setMaximized(false);
				shell.setVisible(false);
			}
		});
		m_browser = new Browser(m_shell, SWT.NONE);
		Utils.ensureProxyBypassLocal();
		m_helper = new IEInitializer(m_browser);
		m_shell.layout();
		// keep Shell disabled, so it will not accept focus/activation
		m_shell.setEnabled(false);
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// IBrowserShell
	//
	////////////////////////////////////////////////////////////////////////////
	public Rectangle computeTrim(int x, int y, int width, int height) {
		return m_shell.computeTrim(x, y, width, height);
	}
	@Override
	public void dispose() {
		m_shell.dispose();
		System.gc();
		System.runFinalization();
		JsValue.mainThreadCleanup();
		super.dispose();
	}
	public boolean isDisposed() {
		return m_shell == null || m_shell.isDisposed();
	}
	public void setBounds(Rectangle bounds) {
		m_shell.setBounds(bounds);
	}
	public void setLocation(int x, int y) {
		m_shell.setLocation(x, y);
	}
	public void setSize(int width, int height) {
		m_shell.setSize(width, height);
	}
	public void setUrl(String url, String moduleName, int timeout, Runnable messageProcessor)
			throws Exception {
		m_browser.setUrl(url);
		// wait for Browser to initialize
		m_helper.wait(timeout, messageProcessor);
		TreeLogger logger = getHost().getLogger();
		try {
			// create module
			ModuleSpaceHost msh = getHost().createModuleSpaceHost(moduleName);
			ModuleSpaceIE6 moduleSpace = new ModuleSpaceIE6(msh, m_helper.m_window, moduleName);
			attachModuleSpace(moduleSpace);
			// release some startup resources
		} finally {
			m_helper.dispose();
		}
	}
	public void setVisible(boolean visible) {
		try {
			m_shell.setVisible(visible);
		} catch (SWTException e) {
			// ignore low level SWT errors
		} catch (IllegalArgumentException e) {
			// ignore low level SWT errors
		}
	}
	public void prepare() {
		setLocation(-10000, -10000);
	}
	public Image createBrowserScreenshot() throws Exception {
		return Utils.makeShot(m_browser);
	}
	public String getUserAgentString() {
		return "ie8";
	}
	////////////////////////////////////////////////////////////////////////////
	//
	// Test/preview
	//
	////////////////////////////////////////////////////////////////////////////
	public void showAsPreview() {
		centerShellInDisplay();
		openShellAsModal();
	}
	private void centerShellInDisplay() {
		try {
			Rectangle containerBounds = m_shell.getDisplay().getClientArea();
			Rectangle shellBounds = m_shell.getBounds();
			int x = Math.max(0, containerBounds.x + (containerBounds.width - shellBounds.width) / 2);
			int y = Math.max(0, containerBounds.y + (containerBounds.height - shellBounds.height) / 3);
			m_shell.setSize(shellBounds.width, shellBounds.height);
			m_shell.setLocation(x, y);
		} catch (SWTException e) {
			// ignore low level SWT errors
		} catch (IllegalArgumentException e) {
			// ignore low level SWT errors
		}
	}
	private void openShellAsModal() {
		// temporary enable to allow user interaction
		m_shell.setEnabled(true);
		try {
			UiUtils.runModalShell(m_shell);
		} finally {
			m_shell.setEnabled(false);
		}
	}
}
