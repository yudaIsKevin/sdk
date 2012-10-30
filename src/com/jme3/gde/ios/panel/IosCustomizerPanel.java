/*
 *  Copyright (c) 2009-2012 jMonkeyEngine
 *  All rights reserved.
 * 
 *  Redistribution and use in source and binary forms, with or without
 *  modification, are permitted provided that the following conditions are
 *  met:
 * 
 *  * Redistributions of source code must retain the above copyright
 *    notice, this list of conditions and the following disclaimer.
 * 
 *  * Redistributions in binary form must reproduce the above copyright
 *    notice, this list of conditions and the following disclaimer in the
 *    documentation and/or other materials provided with the distribution.
 * 
 *  * Neither the name of 'jMonkeyEngine' nor the names of its contributors
 *    may be used to endorse or promote products derived from this software
 *    without specific prior written permission.
 * 
 *  THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS
 *  "AS IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED
 *  TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 *  PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 *  CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 *  EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 *  PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 *  PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 *  LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 *  NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 *  SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

/*
 * LwjglAppletCustomizerPanel.java
 *
 * Created on 11.11.2010, 16:56:53
 */
package com.jme3.gde.ios.panel;

import com.jme3.gde.core.j2seproject.ProjectExtensionProperties;
import com.jme3.gde.ios.IosTool;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import org.openide.util.HelpCtx;

/**
 *
 * @author normenhansen
 */
public class IosCustomizerPanel extends javax.swing.JPanel implements ActionListener {

    private ProjectExtensionProperties properties;

    /**
     * Creates new form LwjglAppletCustomizerPanel
     */
    public IosCustomizerPanel(ProjectExtensionProperties properties) {
        this.properties = properties;
        initComponents();
        HelpCtx.setHelpIDString(this, "jme3.ios");
        jComboBox1.removeAllItems();
        List<String> versions = IosTool.getIosSdkVersions("iPhoneOS");
        for (String string : versions) {
            jComboBox1.addItem(string);
        }
        loadProperties();
    }

    private void loadProperties() {
        boolean enabled = "true".equals(properties.getProperty("ios.enabled"));
        String version = properties.getProperty("ios.version");
        if (version != null) {
            setSelected(version);
        }
        jCheckBox1.setSelected(enabled);
    }

    private void saveProperties() {
        //TODO: check properties
        properties.setProperty("ios.enabled", "" + jCheckBox1.isSelected());
        String version = (String) jComboBox1.getSelectedItem();
        properties.setProperty("ios.version", version);
        properties.setProperty("delete.folder", "" + jCheckBox2.isSelected());
    }

    private void setSelected(String name) {
        for (int i = 0; i < jComboBox1.getItemCount(); i++) {
            String target = (String) jComboBox1.getItemAt(i);
            if (target.equals(name)) {
                jComboBox1.setSelectedIndex(i);
                return;
            }
        }
    }

    public void actionPerformed(ActionEvent e) {
        saveProperties();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jCheckBox1 = new javax.swing.JCheckBox();
        jComboBox1 = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jCheckBox2 = new javax.swing.JCheckBox();
        jTextField1 = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();

        jCheckBox1.setText(org.openide.util.NbBundle.getMessage(IosCustomizerPanel.class, "IosCustomizerPanel.jCheckBox1.text")); // NOI18N

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Item 1", "Item 2", "Item 3", "Item 4" }));

        jLabel1.setText(org.openide.util.NbBundle.getMessage(IosCustomizerPanel.class, "IosCustomizerPanel.jLabel1.text")); // NOI18N

        jCheckBox2.setText(org.openide.util.NbBundle.getMessage(IosCustomizerPanel.class, "IosCustomizerPanel.jCheckBox2.text")); // NOI18N

        jTextField1.setText(org.openide.util.NbBundle.getMessage(IosCustomizerPanel.class, "IosCustomizerPanel.jTextField1.text")); // NOI18N

        jLabel2.setText(org.openide.util.NbBundle.getMessage(IosCustomizerPanel.class, "IosCustomizerPanel.jLabel2.text")); // NOI18N

        jScrollPane1.setEnabled(false);

        jTextArea1.setColumns(20);
        jTextArea1.setLineWrap(true);
        jTextArea1.setRows(5);
        jTextArea1.setText(org.openide.util.NbBundle.getMessage(IosCustomizerPanel.class, "IosCustomizerPanel.jTextArea1.text")); // NOI18N
        jTextArea1.setWrapStyleWord(true);
        jTextArea1.setDisabledTextColor(new java.awt.Color(0, 0, 0));
        jScrollPane1.setViewportView(jTextArea1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1)
                    .addComponent(jCheckBox1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 113, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 75, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(jTextField1)))
                    .addComponent(jCheckBox2, javax.swing.GroupLayout.DEFAULT_SIZE, 500, Short.MAX_VALUE))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jCheckBox1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jCheckBox2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel1))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JCheckBox jCheckBox1;
    private javax.swing.JCheckBox jCheckBox2;
    private javax.swing.JComboBox jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
