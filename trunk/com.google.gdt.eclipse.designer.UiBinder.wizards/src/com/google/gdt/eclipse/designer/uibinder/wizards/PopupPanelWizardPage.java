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
package com.google.gdt.eclipse.designer.uibinder.wizards;

import org.eclipse.wb.internal.core.EnvironmentUtils;

import org.eclipse.jdt.core.IJavaElement;
import org.eclipse.jface.wizard.WizardPage;
import org.eclipse.swt.widgets.Composite;

/**
 * {@link WizardPage} for {@link PopupPanelWizard}.
 * 
 * @author scheglov_ke
 * @coverage GWT.UiBinder.wizards
 */
public final class PopupPanelWizardPage extends UiBinderWizardPage {
  ////////////////////////////////////////////////////////////////////////////
  //
  // Constructor
  //
  ////////////////////////////////////////////////////////////////////////////
  public PopupPanelWizardPage() {
    setTitle("Create UiBinder PopupPanel");
    setImageDescriptor(Activator.getImageDescriptor("wizards/PopupPanel/banner.png"));
    setDescription("Create a UiBinder PopupPanel.");
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Configuration
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  protected void initTypePage(IJavaElement elem) {
    super.initTypePage(elem);
    setSuperClass("com.google.gwt.user.client.ui.PopupPanel", false);
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // Create
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  protected String getTemplatePath_Java() {
    return "templates/PopupPanel.jvt";
  }

  @Override
  protected String getTemplatePath_UI() {
    return "templates/PopupPanel.ui.xml";
  }

  ////////////////////////////////////////////////////////////////////////////
  //
  // GUI
  //
  ////////////////////////////////////////////////////////////////////////////
  @Override
  protected void createLocalControls(Composite parent, int columns) {
    // I always use same names during tests
    if (EnvironmentUtils.DEVELOPER_HOST) {
      setTypeName("PopupPanel_1", true);
    }
  }
}