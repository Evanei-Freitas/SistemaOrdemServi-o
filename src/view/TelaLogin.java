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
import java.sql.*;
import model.ModuloConexao;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;


/**
 * @author Engenheiro de Software e Programador Evanei Freitas
 * Connection conexão com o banco de dados
 * PreparedStatement manipulação de comandos SQL
 * ResultSet resultado da manipulação dos comandos sql
 */
public class TelaLogin extends javax.swing.JFrame {
 //A linha abaixo chamo e uso a variável(conexao)que criei no ModuloConexão e atribuo um valor Nulo.
    Connection conexao = null;
    //Abaixo Crio duas Variáveis Especiais de apoio ao Banco de Dados.
    PreparedStatement pst = null;//(PreparedStatement)Responsável por manipular instruções SQL
    ResultSet rs = null;//( ResultSet )-Exibe os resultados das Instruções SQL
    /**
     * Creates new form TelaLogin
     */
    
    
//    
     /**
      * Método Logar - Loga o sistemana de acordo com Login e Senhas
      * selecionados do banco de dados.
      */
    public void logar() {
        String sql = "Select * From tbusuarios Where login = ? and senha = ?";
        try {
            //As linhas abaixo preparam a consulta ao banco de dados.
            //em função do que foi digitado nas caixas de texto do formulário de login.
            //Os ? será substituídos pelo conteúdo das variáveis.
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuario.getText());
            String captura = new String(txtSenha.getPassword());
            pst.setString(2, captura);

            //A linha abaixo executa a query
            //Query = ("Select * From tbusuario Where login = ? and senha = ?")
            rs = pst.executeQuery();
            //Se existir usuário ou senha correspondente.
            if (rs.next()) {
                //A linha abaixo obtem o conteúdo do campo Perfil da Tabela tbUsuarios
                String perfil = rs.getString(6);
                //System.out.println(perfil);
                //A estrutura abaixo faz o tratamento do perfil do usuário.
                if (perfil.equals("Admin")) {
                    TelaPrincipal telaPrincipal = new TelaPrincipal();
                    telaPrincipal.setVisible(true);
                    TelaPrincipal.jMenuItem_Usuarios.setEnabled(true);
                    TelaPrincipal.jMenu_Relatorio.setEnabled(true);
                    TelaPrincipal.lblUsuarioTelaPrincipal.setText(rs.getString(2));
                    TelaPrincipal.LblPefil.setText(rs.getString(6));
                    this.dispose();
                } else if (perfil.equals("Usuario")) {
                    TelaPrincipal telaPrincipal = new TelaPrincipal();
                    telaPrincipal.setVisible(true);
                    TelaPrincipal.jMenuItem_Usuarios.setEnabled(false);
                    TelaPrincipal.jMenu_Relatorio.setEnabled(false);
                    TelaPrincipal.lblUsuarioTelaPrincipal.setText(rs.getString(2));
                    TelaPrincipal.LblPefil.setText(rs.getString(6));
                    this.dispose();
                } else if (perfil.equals("Atendente")) {
                    TelaPrincipal telaPrincipal = new TelaPrincipal();
                    telaPrincipal.setVisible(true);
                    TelaPrincipal.jMenuItem_Usuarios.setEnabled(false);
                    TelaPrincipal.jMenu_Relatorio.setEnabled(false);
                    TelaPrincipal.lblUsuarioTelaPrincipal.setText(rs.getString(2));
                    TelaPrincipal.LblPefil.setText(rs.getString(6));
                    this.dispose();
                } else if (perfil.equals("Tecnico")) {
                    TelaPrincipal telaPrincipal = new TelaPrincipal();
                    telaPrincipal.setVisible(true);
                    TelaPrincipal.jMenuItem_Usuarios.setEnabled(false);
                    TelaPrincipal.jMenu_Relatorio.setEnabled(false);
                    TelaPrincipal.lblUsuarioTelaPrincipal.setText(rs.getString(2));
                    TelaPrincipal.LblPefil.setText(rs.getString(6));
                    this.dispose();
                } else if (perfil.equals("Gestor")) {
                    TelaPrincipal telaPrincipal = new TelaPrincipal();
                    telaPrincipal.setVisible(true);
                    TelaPrincipal.jMenuItem_Usuarios.setEnabled(true);
                    TelaPrincipal.jMenu_Relatorio.setEnabled(true);
                    TelaPrincipal.lblUsuarioTelaPrincipal.setText(rs.getString(2));
                    TelaPrincipal.LblPefil.setText(rs.getString(6));
                    this.dispose();
                }

                conexao.close();
            } else {
                JOptionPane.showMessageDialog(null, "Usuário e/ou Senha inválido(s)");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    /**
     * Método que faz a verificação se o sistema esta conectado ou
     * não no banco de dados
     * @throws SQLException
     * @throws ClassNotFoundException 
     */
    public TelaLogin() throws SQLException, ClassNotFoundException{
        initComponents();
        ImageIcon imageTitulodaJanela = new ImageIcon("C:/xampp/htdocs/prjinfoTeste/src/img/pc_33X33.png");
        setIconImage(imageTitulodaJanela.getImage());
        conexao = ModuloConexao.conector();
        
        if (conexao != null){
            LblTexto.setText("Conectado...");
            LblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dbok.png")));
        }else{
            LblTexto.setText("Erro : desconectado!...");
            LblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dberror.png")));
        }
    }
    
    /**
 *
 * @author william
 */


   
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_Telalogin = new javax.swing.JPanel();
        jLabel_Usuario = new javax.swing.JLabel();
        jLabel_Senha = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        txtSenha = new javax.swing.JPasswordField();
        btnEntrar = new javax.swing.JButton();
        btnSair = new javax.swing.JButton();
        LblTexto = new javax.swing.JLabel();
        LblStatus = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setTitle("Tela de Login");
        setLocationByPlatform(true);
        setName("frmTelaLogin"); // NOI18N
        setResizable(false);

        jPanel_Telalogin.setBackground(new java.awt.Color(255, 255, 255));

        jLabel_Usuario.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel_Usuario.setText("Usuário ");

        jLabel_Senha.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel_Senha.setText("Senha");

        txtUsuario.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        txtSenha.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        btnEntrar.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnEntrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked.png"))); // NOI18N
        btnEntrar.setText("Entrar");
        btnEntrar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnEntrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnEntrarActionPerformed(evt);
            }
        });

        btnSair.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnSair.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Sair.png"))); // NOI18N
        btnSair.setText("Sair");
        btnSair.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSair.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSairActionPerformed(evt);
            }
        });

        LblTexto.setFont(new java.awt.Font("Arial", 2, 14)); // NOI18N
        LblTexto.setForeground(new java.awt.Color(51, 102, 255));
        LblTexto.setText("Status");

        LblStatus.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/dberror.png"))); // NOI18N

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 210, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );

        jLabel1.setBackground(new java.awt.Color(0, 51, 204));
        jLabel1.setFont(new java.awt.Font("Arial", 0, 36)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(0, 51, 204));
        jLabel1.setText("Tela de Login");

        javax.swing.GroupLayout jPanel_TelaloginLayout = new javax.swing.GroupLayout(jPanel_Telalogin);
        jPanel_Telalogin.setLayout(jPanel_TelaloginLayout);
        jPanel_TelaloginLayout.setHorizontalGroup(
            jPanel_TelaloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_TelaloginLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(30, 30, 30)
                .addGroup(jPanel_TelaloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jLabel1, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel_TelaloginLayout.createSequentialGroup()
                        .addGroup(jPanel_TelaloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_TelaloginLayout.createSequentialGroup()
                                .addComponent(jLabel_Senha)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED))
                            .addGroup(jPanel_TelaloginLayout.createSequentialGroup()
                                .addComponent(jLabel_Usuario)
                                .addGap(5, 5, 5)))
                        .addGroup(jPanel_TelaloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel_TelaloginLayout.createSequentialGroup()
                                .addComponent(btnEntrar)
                                .addGap(26, 26, 26)
                                .addComponent(btnSair))
                            .addComponent(txtUsuario)
                            .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_TelaloginLayout.createSequentialGroup()
                                .addComponent(LblTexto, javax.swing.GroupLayout.PREFERRED_SIZE, 121, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(LblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 58, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(56, Short.MAX_VALUE))
        );
        jPanel_TelaloginLayout.setVerticalGroup(
            jPanel_TelaloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_TelaloginLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addGap(27, 27, 27)
                .addGroup(jPanel_TelaloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_Usuario)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_TelaloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel_Senha)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_TelaloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnEntrar, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnSair))
                .addGroup(jPanel_TelaloginLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_TelaloginLayout.createSequentialGroup()
                        .addGap(39, 39, 39)
                        .addComponent(LblStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 63, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(26, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_TelaloginLayout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(LblTexto)
                        .addGap(42, 42, 42))))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_Telalogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_Telalogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        setSize(new java.awt.Dimension(599, 366));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnEntrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnEntrarActionPerformed
        //Codigo (Logar - Entrar no Sistema )
        logar();
    }//GEN-LAST:event_btnEntrarActionPerformed

    private void btnSairActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSairActionPerformed
        // Código do botão sair
        System.exit(0);
    }//GEN-LAST:event_btnSairActionPerformed

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
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaLogin.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    new TelaLogin().setVisible(true);
                } catch (SQLException ex) {
                    Logger.getLogger(TelaLogin.class.getName()).log(Level.SEVERE, null, ex);
                } catch (ClassNotFoundException ex) {
                    Logger.getLogger(TelaLogin.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel LblStatus;
    private javax.swing.JLabel LblTexto;
    private javax.swing.JButton btnEntrar;
    private javax.swing.JButton btnSair;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel_Senha;
    private javax.swing.JLabel jLabel_Usuario;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel_Telalogin;
    public static javax.swing.JPasswordField txtSenha;
    public static javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables
}
