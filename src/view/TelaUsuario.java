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
import java.awt.Image;
import java.io.FileInputStream;
import java.io.InputStream;
import utils.SoNumeros;
import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import javax.swing.JFileChooser;
import model.ModuloConexao;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;
import net.proteanit.sql.DbUtils;


/**
 * @author Engenheiro de Software e Programador Evanei Freitas
 * Connection conexão com o banco de dados
 * PreparedStatement Manipulação de instruções SQL
 * ResultSet Manipulação dos Resultados SQL
 */
public class TelaUsuario extends javax.swing.JFrame {
    /**
     * Instanciar um objeto para o fluxo de bytes -FileInputStream fis
     */
    private FileInputStream fis;
    /**
     * Criando uma variável global para armazenar o tamanho da imagem(bytes)
     */
    private int tamanho;
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    
    
    /**
     * Método construtor da Classe
     */
    public TelaUsuario() {
        initComponents();
        ImageIcon imageTitulodaJanela = new ImageIcon("C:/xampp/htdocs/prjinfoTeste/src/img/pc_33X33.png");
        setIconImage(imageTitulodaJanela.getImage());
        txtCpf.setDocument(new SoNumeros());
        txtFone.setDocument(new SoNumeros());
        
        try {
            conexao = ModuloConexao.conector();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro de : SQLException "+ ex);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Erro de : ClassNotFoundException"+ ex);
        }
        
        inicializarCadFunc();
        
    }
    
    
    /**
     * Método para adicionar Usuários (CRUD) = CREATE
     */
    private void adicionar() {
        String sql = "Insert into tbusuarios(usuario, fone, login, senha, perfil, cpf, foto) Values(?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtUsuario.getText());
            pst.setString(2, txtFone.getText());
            pst.setString(3, txtLogin.getText());
            pst.setString(4, txtSenha.getText());
            pst.setString(5, jComboBox_Perfil.getSelectedItem().toString());
            pst.setString(6, txtCpf.getText());
            pst.setBlob(7, fis,tamanho);
			

            // Validação dos Campos do formulário, para não haver campos sem informações.
            if (txtUsuario.getText().isEmpty() || (txtLogin.getText().isEmpty()) || (txtSenha.getText().isEmpty())
                    || (jComboBox_Perfil.getSelectedItem().equals(null)) || (txtCpf.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos Obrigatórios.");
            } else {

                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                    limparTela();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Não foi possível cadastrar o Usuário. "
                    + "Banco de Dados! "+e);
        }
    }
    
    
    
    
    /**
     * Ler, Pesquisar -(CRUD) = READ
     * Método para pesquisar clientes pelo nome com filtro.
     */
    private void pesquisarUsuario() {
        String sql = "Select * From tbusuarios Where usuario Like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //Passando o conteúdo da caixa de pesquisa para o Interroga.
            // atenção a "%", que é a continuação da string SQL.
            pst.setString(1, txtUsuario.getText() + "%");
            rs = pst.executeQuery();
            //A linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblUsuarios.setModel(DbUtils.resultSetToTableModel(rs));
            btnNovo.setEnabled(true);
            
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível localizar o Usuarios!");
        }
    }
    
    
     /**
     * Ler, Pesquisar -(CRUD) = READ
     * Método para consultar digitando o número do CPF
     */
      private void consultarCPF() {
        String sql = "Select * From tbusuarios where cpf = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCpf.getText());
            rs = pst.executeQuery();

            if (rs.next()) {
                txtCodigo.setText(rs.getString(1));
                txtUsuario.setText(rs.getString(2));
                txtFone.setText(rs.getString(3));
                txtLogin.setText(rs.getString(4));
                txtSenha.setText(rs.getString(5));
                //A linha abaixo se refere ao Combobox.
                jComboBox_Perfil.setSelectedItem(rs.getString(6));
                txtCpf.setText(rs.getString(7));
               
            } else {
                JOptionPane.showMessageDialog(null, "Usuário não Cadastrado ou , " + "\nFavor digite o número de CPF do usuário para "
                        + "ser pesquisado!\nOBS: Você deve digitar o número do CPF do Usuário "
                        + "completo!");

                limparTela();
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro : Não foi possível realizar a consulta "
                    + "desejada! " + e);
        }
    }
    
      
    /**
     * Método Atualizar dados no Banco de Dados - (CRUD) = UPDATE
     */
    private void atualizar() {
        String sql = "Update tbusuarios set usuario=?, fone=?, login=?, senha=?, perfil=?, cpf=? Where iduser=?";
        try {
            pst = conexao.prepareStatement(sql);
            //
            pst.setString(1, txtUsuario.getText());
            pst.setString(2, txtFone.getText());
            pst.setString(3, txtLogin.getText());
            pst.setString(4, txtSenha.getText());
            pst.setString(5, jComboBox_Perfil.getSelectedItem().toString());
            pst.setString(6, txtCpf.getText());
            //lblFoto.setIcon(new ImageIcon(rs.getBytes(7)));
            pst.setString(7, txtCodigo.getText());
            

            // Código que valida os campos, se eles estiverem vazios o sistema informará que deve ser preenchidos.
            if (txtUsuario.getText().isEmpty() || (txtLogin.getText().isEmpty()) || (txtSenha.getText().isEmpty())
                    || (jComboBox_Perfil.getSelectedItem().equals(null)) || (txtCpf.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos Obrigatórios.");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados Atualizados com sucesso!");
                    limparTela();
                    pesquisarUsuario();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível atualizar os dados do usuário. ");
        }
    }
      
      
    
    /**
     * Método para Excluir dados do Banco de Dados - (CRUD) = DELETE
     */
    private void deletar() {
        //A estrutura confirma a remoção(Exclusão do usuário).
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que você deseja excluir esse usuário? ",
                "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "Delete From tbusuarios Where iduser = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCodigo.getText());
                if (txtUsuario.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Favor fazer uma consulta e escolha os dados a serem excluidos.");
                } else {
                    int apagado = pst.executeUpdate();
                    if (apagado > 0) {
                        JOptionPane.showMessageDialog(null, "Cadastro Excluido com Sucesso! ");
                        limparTela();
                        pesquisarUsuario();
                    }

                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro : \nNão foi possível Excluir os Dados!\n "
                        + "Voce deve primeiro consultar o usuário a ser Excluido!" + e);
            }
        }

    }
    
    
      
      /**
       * Método para setar os campos do formulário com o conteúdo da tabela.
       */
    public void setarCampos() {
        int setar = tblUsuarios.getSelectedRow();
        txtCodigo.setText(tblUsuarios.getModel().getValueAt(setar, 0).toString());
        txtUsuario.setText(tblUsuarios.getModel().getValueAt(setar, 1).toString());
        txtCpf.setText(tblUsuarios.getModel().getValueAt(setar, 6).toString());
        txtFone.setText(tblUsuarios.getModel().getValueAt(setar, 2).toString());
        txtLogin.setText(tblUsuarios.getModel().getValueAt(setar, 3).toString());  
        txtSenha.setText(tblUsuarios.getModel().getValueAt(setar, 4).toString());
        jComboBox_Perfil.setSelectedItem(tblUsuarios.getModel().getValueAt(setar,5));
 
        txtUsuario.setEnabled(true);
        txtCpf.setEnabled(true);
        txtFone.setEnabled(true);
        txtLogin.setEnabled(true);
        txtSenha.setEnabled(true);
        
        jComboBox_Perfil.setEnabled(true);
        
        tblUsuarios.setEnabled(true);
        
        btnNovo.setEnabled(false);
        btnSalvar.setEnabled(false);
        btnLimparTela.setEnabled(true);
        btnPesquisarCpf.setEnabled(true);
        btnAtualiz.setEnabled(true);
        btnExcluir.setEnabled(true);
       
    }
      
      
    
    /**
     * Método Limpar a Tela.
     */
    public void limparTela() {
        txtCodigo.setText(null);
        txtUsuario.setText(null);
        txtFone.setText(null);
        txtLogin.setText(null);
        txtSenha.setText(null);
        //cboTxtUsuPerfil.setSelectedItem(null);
        txtCpf.setText(null);
        
    }

    
    /**
     * Método para inicializar o cadastro de funcionario
     */
    private void inicializarCadFunc(){
        txtUsuario.setEnabled(true);
        txtCpf.setEnabled(true);
        txtFone.setEnabled(false);
        txtLogin.setEnabled(false);
        txtSenha.setEnabled(false);
        jComboBox_Perfil.setEnabled(false);
        
        tblUsuarios.setEnabled(false);
        
        btnNovo.setEnabled(false);
        btnSalvar.setEnabled(false);
        btnLimparTela.setEnabled(false);
        btnPesquisarCpf.setEnabled(true);
        btnAtualiz.setEnabled(false);
        btnExcluir.setEnabled(false);
    }
    
    
    /**
     * Método para carregar foto
     */
    private void carregarFoto(){
        JFileChooser jfc = new JFileChooser();
        jfc.setDialogTitle("Selecionar Arquivo");
        jfc.setFileFilter(new FileNameExtensionFilter("Arquivo de Imagens (*.PNG,*.JPG,*.JPEG)","png","jpg","jpeg"));
        int resultado = jfc.showOpenDialog(this);
        if(resultado == JFileChooser.APPROVE_OPTION){
            try {
                fis = new FileInputStream(jfc.getSelectedFile());
                tamanho = (int)jfc.getSelectedFile().length();
              
               
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
   
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel_Usuario = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txtLogin = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        jLabel5 = new javax.swing.JLabel();
        txtCpf = new javax.swing.JTextField();
        jComboBox_Perfil = new javax.swing.JComboBox<>();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        txtFone = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtUsuario = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblUsuarios = new javax.swing.JTable();
        btnSalvar = new javax.swing.JButton();
        btnPesquisarCpf = new javax.swing.JButton();
        btnLimparTela = new javax.swing.JButton();
        btnAtualiz = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnNovo = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Usuários");
        setLocationByPlatform(true);
        setResizable(false);

        jPanel_Usuario.setBackground(new java.awt.Color(255, 255, 255));

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cadastro de Usuários ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(277, 277, 277)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel1)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        jLabel2.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel2.setText("Cód :");

        txtCodigo.setEditable(false);
        txtCodigo.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel3.setText("Login :");

        txtLogin.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel4.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel4.setText("Senha :");

        txtSenha.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("Cpf :");

        txtCpf.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtCpf.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCpfKeyTyped(evt);
            }
        });

        jComboBox_Perfil.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jComboBox_Perfil.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Admin", "Atendente", "Usuario", "Tecnico", "Gestor", " ", " ", " ", " ", " ", " " }));

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setText("Perfil :");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("Fone :");

        txtFone.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtFone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFoneKeyTyped(evt);
            }
        });

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setText("Usuário :");

        txtUsuario.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtUsuario.setForeground(new java.awt.Color(51, 51, 51));
        txtUsuario.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtUsuarioKeyReleased(evt);
            }
        });

        tblUsuarios = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblUsuarios.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tblUsuarios.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "iduser", "usuário :", "fone :", "login :", "senha :", "perfil :", "cpf : "
            }
        ));
        tblUsuarios.setFocusable(false);
        tblUsuarios.getTableHeader().setResizingAllowed(false);
        tblUsuarios.getTableHeader().setReorderingAllowed(false);
        tblUsuarios.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblUsuariosMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblUsuarios);

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked.png"))); // NOI18N
        btnSalvar.setToolTipText("Salvar dados");
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnPesquisarCpf.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnPesquisarCpf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa2_2.png"))); // NOI18N
        btnPesquisarCpf.setToolTipText("Pesquisar pelo CPF");
        btnPesquisarCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarCpfActionPerformed(evt);
            }
        });

        btnLimparTela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancel_2.jpg"))); // NOI18N
        btnLimparTela.setToolTipText("Cancelar e Limpar a tela");
        btnLimparTela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimparTela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparTelaActionPerformed(evt);
            }
        });

        btnAtualiz.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/upgrade_2.png"))); // NOI18N
        btnAtualiz.setToolTipText("Atualizar Dados");
        btnAtualiz.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualiz.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizActionPerformed(evt);
            }
        });

        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Excluir.jpg"))); // NOI18N
        btnExcluir.setToolTipText("Excluir dados");
        btnExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/novo.jpg"))); // NOI18N
        btnNovo.setToolTipText("Novo dados para se adicionados");
        btnNovo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel_UsuarioLayout = new javax.swing.GroupLayout(jPanel_Usuario);
        jPanel_Usuario.setLayout(jPanel_UsuarioLayout);
        jPanel_UsuarioLayout.setHorizontalGroup(
            jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel7, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel4, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(jLabel2)
                        .addComponent(jLabel3)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimparTela, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAtualiz, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                        .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtLogin)
                            .addComponent(txtSenha)
                            .addComponent(txtFone, javax.swing.GroupLayout.DEFAULT_SIZE, 226, Short.MAX_VALUE))
                        .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                                .addGap(18, 18, 18)
                                .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                                        .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 248, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnPesquisarCpf))))
                            .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                                .addGap(37, 37, 37)
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox_Perfil, javax.swing.GroupLayout.PREFERRED_SIZE, 153, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 797, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 37, Short.MAX_VALUE))
        );
        jPanel_UsuarioLayout.setVerticalGroup(
            jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel8)
                    .addComponent(txtUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                        .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel3)
                            .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(17, 17, 17)
                        .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel4))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtFone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel7)))
                    .addGroup(jPanel_UsuarioLayout.createSequentialGroup()
                        .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel5)
                            .addComponent(btnPesquisarCpf))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel6)
                            .addComponent(jComboBox_Perfil, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel_UsuarioLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimparTela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnExcluir, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAtualiz, javax.swing.GroupLayout.Alignment.LEADING))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 200, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(49, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel_Usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(863, 558));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtUsuarioKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtUsuarioKeyReleased
        //O evento(KeyReleased) ele fazra consulta enquanto o usuário esta digitando.
       controlartxtUsuarioKeyReleased();
       
    }//GEN-LAST:event_txtUsuarioKeyReleased

    private void tblUsuariosMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblUsuariosMouseClicked
        // Código do botão setar campos no formulário
        setarCampos();
    }//GEN-LAST:event_tblUsuariosMouseClicked

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
        adicionar();
        limparTela();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnPesquisarCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarCpfActionPerformed
        // Código do botão pesquisar CPF:
        controlarBotaobtnPesquisarCpf();
        
    }//GEN-LAST:event_btnPesquisarCpfActionPerformed

    private void btnLimparTelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparTelaActionPerformed
        // Código do botão Limpar a Tela.
        controlarbotaoLimpar();
    }//GEN-LAST:event_btnLimparTelaActionPerformed

    private void btnAtualizActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizActionPerformed
        // Código do botão Atualizar dados.
        controlarBotaobtnAtualizar();
    }//GEN-LAST:event_btnAtualizActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // Código do botão Deletar dados:
        deletar();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        controlarBotaobtnNovo();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void txtCpfKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCpfKeyTyped
        //Limitando a Quantidade de Caracteres
        int numerosCaracteres = 11;
        if (txtCpf.getText().length()>= numerosCaracteres) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Pode-se digitar somente 11Caracteres e sem espaços, sem pontos!");
        } 
     
    }//GEN-LAST:event_txtCpfKeyTyped

    private void txtFoneKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoneKeyTyped
        //Limitando a Quantidade de Caracteres
        int numerosCaracteres = 11;
        if (txtFone.getText().length()>= numerosCaracteres) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Pode-se digitar somente 11Caracteres e sem espaços, sem pontos!");
        } 
    }//GEN-LAST:event_txtFoneKeyTyped

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
            java.util.logging.Logger.getLogger(TelaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaUsuario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(() -> {
            new TelaUsuario().setVisible(true);
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualiz;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimparTela;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisarCpf;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JComboBox<String> jComboBox_Perfil;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel_Usuario;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblUsuarios;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCpf;
    private javax.swing.JTextField txtFone;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JTextField txtUsuario;
    // End of variables declaration//GEN-END:variables


   
    /***
     * Método de controle de botões e caixas de texto para 
     * O evento do botão txtUsuarioKeyReleased
     */
    
    public void controlartxtUsuarioKeyReleased(){
         pesquisarUsuario();
        tblUsuarios.setEnabled(true);
    }
    
    
    
    
    /***
     * Método de controle de botões e caixas de texto para 
     * O evento do botão btnLimparTelaActionPerformed
     */
    public void controlarbotaoLimpar(){
        limparTela();
        btnNovo.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnLimparTela.setEnabled(false);
        btnPesquisarCpf.setEnabled(true);
        btnAtualiz.setEnabled(false);
        btnExcluir.setEnabled(false);
        
        tblUsuarios.setEnabled(true);
        
        txtUsuario.setEnabled(true);
        txtCpf.setEnabled(true);
        txtFone.setEnabled(false);
        txtLogin.setEnabled(false);
        txtSenha.setEnabled(false);
        jComboBox_Perfil.setEnabled(false);
    }
    
    
    /***
     * Método de controle de botões e caixas de texto para 
     * O evento do botão btnAtualizActionPerformed
     */
    
    public void controlarBotaobtnAtualizar(){
        atualizar();
        btnNovo.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnLimparTela.setEnabled(false);
        btnPesquisarCpf.setEnabled(true);
        btnAtualiz.setEnabled(false);
        btnExcluir.setEnabled(false);
        txtUsuario.setEnabled(true);
        txtCpf.setEnabled(true);
        txtFone.setEnabled(false);
        txtLogin.setEnabled(false);
        txtSenha.setEnabled(false);
        jComboBox_Perfil.setEnabled(false);
        
        tblUsuarios.setEnabled(false);
    }
    
    
    /***
     * Método de controle de botões e caixas de texto para 
     * O evento do botão btnNovoActionPerformed
     */
     public void controlarBotaobtnNovo(){
        limparTela();
        btnNovo.setEnabled(false);
        btnSalvar.setEnabled(true);
        btnLimparTela.setEnabled(true);
        btnPesquisarCpf.setEnabled(true);
        btnAtualiz.setEnabled(true);
        tblUsuarios.setEnabled(true);
        txtUsuario.setEnabled(true);
        txtCpf.setEnabled(true);
        txtFone.setEnabled(true);
        txtLogin.setEnabled(true);
        txtSenha.setEnabled(true);
        jComboBox_Perfil.setEnabled(true);
     }
    
    /***
     * Método de controle de botões e caixas de texto para 
     * O evento do botão btnPesquisarCpfActionPerformed
     */
     public void controlarBotaobtnPesquisarCpf(){
        consultarCPF();
        pesquisarUsuario();
        tblUsuarios.setEnabled(true);
     }
     
    
}
