/*
 * The MIT License
 *
 * Copyright 2023 Engenheiro de Software - Evanei Freitas.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
/**
 * A permissão é concedida, gratuitamente, a qualquer pessoa que obtenha uma cópia
 * deste software e arquivos de documentação associados (o "Software"), para lidar
 * no Software sem restrições, incluindo, sem limitação, os direitos
 * para usar, copiar, modificar, mesclar, publicar, distribuir, sublicenciar e/ou vender
 * cópias do Software, e para permitir que as pessoas a quem o Software é
 * fornecido para fazê-lo, sujeito às seguintes condições:
 *
 * O aviso de direitos autorais acima e este aviso de permissão devem ser incluídos em
 * todas as cópias ou partes substanciais do Software.
 *
 ****************************************************************************
 *
 * O SOFTWARE É FORNECIDO "COMO ESTÁ", SEM GARANTIA DE QUALQUER TIPO, EXPRESSA
 * OU IMPLÍCITA, INCLUINDO MAS, NÃO SE LIMITANDO ÀS GARANTIAS DE COMERCIALIZAÇÃO,
 * ADEQUAÇÃO PARA UM FIM ESPECÍFICO E NÃO VIOLAÇÃO.
 * EM NENHUM CASO O OS AUTORES OU DETENTORES DOS DIREITOS AUTORAIS SERÃO
 * RESPONSÁVEIS POR QUALQUER REIVINDICAÇÃO, DANOS OU OUTROS RESPONSABILIDADE,
 * SEJA EM UMA AÇÃO DE CONTRATO, ILÍCITO OU DE OUTRA FORMA, DECORRENTE DE,
 * FORA DE OU EM CONEXÃO COM O SOFTWARE OU O USO OU OUTROS NEGÓCIOS EM
 * O SOFTWARE.
 */
package view;

import model.ModuloConexao;
import java.util.Date;
import java.sql.*;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;

public class TelaPrincipal extends javax.swing.JFrame {
    DateFormat formatoHora = new SimpleDateFormat("HH:mm:ss");
    Connection conexao = null;

    /**
     * O Construtor da classe Inicia os componentes da classe e Inicia a conexão
     * com o banco de dados
     */
    public TelaPrincipal() {
        initComponents();
        ImageIcon imageTitulodaJanela = new ImageIcon("C:/xampp/htdocs/prjinfoTeste/src/img/pc_33X33.png");
        setIconImage(imageTitulodaJanela.getImage());
        //this.setIconImage(new ImageIcon(getClass().getResource("C:/xampp/htdocs/prjinfoTeste/src/img/pc_33X33.png")).getImage());
        try {
            conexao = ModuloConexao.conector();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro de : SQLException " + ex);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Erro de : ClassNotFoundException" + ex);
        }
        setarData();
        Thread horaAtual = new Thread(new TelaPrincipal.HoraThread());
        horaAtual.start();
    }
    
    class HoraThread implements Runnable{

        @Override
        public void run() {
          while(true){
              lblHora.setText(formatoHora.format(new Date()));
              try {
                  Thread.sleep(1000);
                  
              } catch (InterruptedException ex) {
                   System.out.println("Thread foi finalizada" + ex);
              }
          }
        }
        
    }

    /**
     * O método Imprime o relatório com os dados de todos os clientes
     * cadastrados no banco de Dados
     */
    private void imprimirRelatorioClientes() {
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a Emissão deste Relatório? ", ""
                + "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            //Emitindo o Relatório com o FrameWork JasperReports.
            try {
                // Usando a Classe JasperPrint para preparar a Impressão de um Relatórioclientes.
                JasperPrint print = JasperFillManager.fillReport("C:/xampp/htdocs/prjinfoTeste/reports/clientes.jasper", null, conexao);
                // A linha abaixo exibe o relatório.
                JasperViewer.viewReport(print, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * O método Imprime o relatório com os dados de todos os serviços
     * cadastrados no banco de Dados
     */
    private void imprimirRelatoruiServiços() {
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a Emissão deste Relatório? ", ""
                + "Atenção!", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            // Emitindo o Relatório com o FrameWork JasperReports.
            try {
                // Usando a Classe JasperPrint para preparar a Impressão de um Relatórioclientes.
                JasperPrint print = JasperFillManager.fillReport("C:/xampp/htdocs/prjinfoTeste/reports/servicos.jasper", null, conexao);
                // A linha abaixo exibe o relatório.
                JasperViewer.viewReport(print, false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        lblUsuarioTelaPrincipal = new javax.swing.JLabel();
        LblPefil = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jPanel3 = new javax.swing.JPanel();
        lbldata = new javax.swing.JLabel();
        lblHora = new javax.swing.JLabel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu_Cadastros = new javax.swing.JMenu();
        jMenuItem_Usuarios = new javax.swing.JMenuItem();
        jMenuItem_Clientes = new javax.swing.JMenuItem();
        jMenuItem_Os = new javax.swing.JMenuItem();
        jSeparator1 = new javax.swing.JPopupMenu.Separator();
        jMenuItem_SairSistema = new javax.swing.JMenuItem();
        jMenu_Relatorio = new javax.swing.JMenu();
        memRelClientes = new javax.swing.JMenuItem();
        memRelServicos = new javax.swing.JMenuItem();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Sistema de Ordem de Serviços (SISOS) - serialVersionUID = 1.1");
        setLocationByPlatform(true);
        setResizable(false);

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        lblUsuarioTelaPrincipal.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        lblUsuarioTelaPrincipal.setText("Status1");

        LblPefil.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        LblPefil.setForeground(new java.awt.Color(153, 0, 51));
        LblPefil.setText("Status2");

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/pc.png"))); // NOI18N

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(153, 153, 153));
        jLabel2.setText("Engenheiro de Sistemas (Software )");

        jLabel4.setFont(new java.awt.Font("Comic Sans MS", 2, 14)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(153, 153, 153));
        jLabel4.setText("Evanei Freitas .");

        jLabel5.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(153, 153, 153));
        jLabel5.setText("prwilliannascimento@hotmail.com");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel1Layout.createSequentialGroup()
                                .addComponent(lblUsuarioTelaPrincipal, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(LblPefil, javax.swing.GroupLayout.PREFERRED_SIZE, 117, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addGap(59, 59, 59)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel5, javax.swing.GroupLayout.PREFERRED_SIZE, 263, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2, javax.swing.GroupLayout.PREFERRED_SIZE, 240, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addContainerGap(16, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(45, 45, 45)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lblUsuarioTelaPrincipal)
                    .addComponent(LblPefil))
                .addGap(18, 18, 18)
                .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 170, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 413, Short.MAX_VALUE)
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addGap(36, 36, 36))
        );

        jPanel2.setBackground(new java.awt.Color(0, 102, 153));

        jLabel3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/logotipo-473x315.jpeg"))); // NOI18N

        jPanel3.setBackground(new java.awt.Color(255, 255, 255));

        lbldata.setFont(new java.awt.Font("Tahoma", 2, 19)); // NOI18N
        lbldata.setForeground(new java.awt.Color(0, 51, 102));
        lbldata.setText("data :");

        lblHora.setFont(new java.awt.Font("Tahoma", 2, 19)); // NOI18N
        lblHora.setForeground(new java.awt.Color(0, 0, 102));
        lblHora.setHorizontalAlignment(javax.swing.SwingConstants.LEFT);
        lblHora.setText("hora :");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(lbldata, javax.swing.GroupLayout.PREFERRED_SIZE, 386, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 273, Short.MAX_VALUE)
                .addComponent(lblHora, javax.swing.GroupLayout.PREFERRED_SIZE, 235, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(116, 116, 116))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lbldata)
                    .addComponent(lblHora))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(172, 172, 172)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(81, 81, 81)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jMenu_Cadastros.setText("Cadastros");

        jMenuItem_Usuarios.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/seta.png"))); // NOI18N
        jMenuItem_Usuarios.setText("Usuarios");
        jMenuItem_Usuarios.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_UsuariosActionPerformed(evt);
            }
        });
        jMenu_Cadastros.add(jMenuItem_Usuarios);

        jMenuItem_Clientes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/seta.png"))); // NOI18N
        jMenuItem_Clientes.setText("Clientes");
        jMenuItem_Clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_ClientesActionPerformed(evt);
            }
        });
        jMenu_Cadastros.add(jMenuItem_Clientes);

        jMenuItem_Os.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/seta.png"))); // NOI18N
        jMenuItem_Os.setText("Ordem Serviços(OS)");
        jMenuItem_Os.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_OsActionPerformed(evt);
            }
        });
        jMenu_Cadastros.add(jMenuItem_Os);
        jMenu_Cadastros.add(jSeparator1);

        jMenuItem_SairSistema.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Sair.png"))); // NOI18N
        jMenuItem_SairSistema.setText("Sair...");
        jMenuItem_SairSistema.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem_SairSistemaActionPerformed(evt);
            }
        });
        jMenu_Cadastros.add(jMenuItem_SairSistema);

        jMenuBar1.add(jMenu_Cadastros);

        jMenu_Relatorio.setText("Relatorios");

        memRelClientes.setText("Clientes");
        memRelClientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memRelClientesActionPerformed(evt);
            }
        });
        jMenu_Relatorio.add(memRelClientes);

        memRelServicos.setText("Serviços");
        memRelServicos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                memRelServicosActionPerformed(evt);
            }
        });
        jMenu_Relatorio.add(memRelServicos);

        jMenuBar1.add(jMenu_Relatorio);

        setJMenuBar(jMenuBar1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jMenuItem_UsuariosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_UsuariosActionPerformed
        // TODO add your handling code here:
        TelaUsuario telaUsuario = new TelaUsuario();
        telaUsuario.setVisible(true);

    }//GEN-LAST:event_jMenuItem_UsuariosActionPerformed

    private void jMenuItem_SairSistemaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_SairSistemaActionPerformed
        // Código para sair do Sistema.
        System.exit(0);
    }//GEN-LAST:event_jMenuItem_SairSistemaActionPerformed

    private void jMenuItem_ClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_ClientesActionPerformed
        // Chamo o Formulario de cadastro de Clientes.
        TelaCliente telaCliente = new TelaCliente();
        telaCliente.setVisible(true);
    }//GEN-LAST:event_jMenuItem_ClientesActionPerformed

    private void jMenuItem_OsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem_OsActionPerformed
        // Chamo o Formulário de cadastro de (OS) - Ordem de Serviços.
        TelaOS telaOrdemServicos = new TelaOS();
        telaOrdemServicos.setVisible(true);

    }//GEN-LAST:event_jMenuItem_OsActionPerformed

    private void memRelClientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memRelClientesActionPerformed
        // Gerando um  Relatório de Clientes
        imprimirRelatorioClientes();
    }//GEN-LAST:event_memRelClientesActionPerformed

    private void memRelServicosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_memRelServicosActionPerformed
        // Gerando um  Relatório de Serviços.
        imprimirRelatoruiServiços();
    }//GEN-LAST:event_memRelServicosActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaPrincipal.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaPrincipal().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    public static javax.swing.JLabel LblPefil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem_Clientes;
    private javax.swing.JMenuItem jMenuItem_Os;
    private javax.swing.JMenuItem jMenuItem_SairSistema;
    public static javax.swing.JMenuItem jMenuItem_Usuarios;
    private javax.swing.JMenu jMenu_Cadastros;
    public static javax.swing.JMenu jMenu_Relatorio;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPopupMenu.Separator jSeparator1;
    private javax.swing.JLabel lblHora;
    public static javax.swing.JLabel lblUsuarioTelaPrincipal;
    private javax.swing.JLabel lbldata;
    private javax.swing.JMenuItem memRelClientes;
    private javax.swing.JMenuItem memRelServicos;
    // End of variables declaration//GEN-END:variables

    /**
     * Método que exibe a data do sistema quando o programa é inicializado
     */
    private void setarData() {
        Date data = new Date();
        DateFormat formatador = DateFormat.getDateInstance(DateFormat.FULL);
        lbldata.setText(formatador.format(data));
        
//        Calendar now = Calendar.getInstance();
//        lblHora.setText(String.format("%1$tH:%1$tM:%1$tS",now));
    }
   

}
