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
import java.io.FileInputStream;
import utils.SoNumeros;
import java.sql.*;
import javax.swing.ImageIcon;
import model.ModuloConexao;
import net.proteanit.sql.DbUtils;
import javax.swing.JOptionPane;

/**
 * @author Engenheiro de Software e Programador Evanei Freitas
 * Connection conexão com o banco de dados
 * PreparedStatement Manipulação de instruções SQL
 * ResultSet Manipulação dos resultados das instruções SQL
 */
public class TelaCliente extends javax.swing.JFrame {
     /**
     * Instanciar um objeto para o fluxo de bytes -FileInputStream fis
     */
    private FileInputStream fis;
    /**
     * Criando uma variável global para armazenar o tamanho da imagem(bytes)
     */
    private int tamanho;
    
//A linha abaixo chamo e uso a variável(conexao)que criei no ModuloConexão e atribuo um valor Nulo.
    Connection conexao = null;
    //Abaixo Crio duas Variáveis Especiais de apoio ao Banco de Dados.
    PreparedStatement pst = null;//(PreparedStatement)Responsável por manipular instruções SQL
    ResultSet rs = null;//( ResultSet )-Exibe os resultados das Instruções SQL
    
    
    
    /**
     * Método construtor, inicializa os componentes do sistema
     */
    public TelaCliente() {
        initComponents();
        ImageIcon imageTitulodaJanela = new ImageIcon("C:/xampp/htdocs/prjinfoTeste/src/img/pc_33X33.png");
        setIconImage(imageTitulodaJanela.getImage());
        
        txtCpfCliente.setDocument(new SoNumeros());
        txtFoneCliente.setDocument(new SoNumeros());
        
         try {
            conexao = ModuloConexao.conector();
        } catch (SQLException ex) {
             JOptionPane.showMessageDialog(null,"Erro de : SQLException "+ ex);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Erro de : ClassNotFoundException"+ ex);
        }
         
         inicializarCadCliente();
    }
    
    
    /**
     * Método para Cadastrar Clientes no BD.
     */
    private void adicionarClientes() {
        String sql = "Insert into tbclientes(nomecli, endcli, fonecli, emailcli, cpf) Values(?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtNomeCliente.getText());
            pst.setString(2, txtEnderecoCliente.getText());
            pst.setString(3, txtFoneCliente.getText());
            pst.setString(4, txtEmailCliente.getText());
            pst.setString(5, txtCpfCliente.getText());
        	

            // Validação dos Campos do formulário, para não haver campos sem informações.
            if (txtNomeCliente.getText().isEmpty() || (txtEnderecoCliente.getText().isEmpty()) || (txtFoneCliente.getText().isEmpty())
                    || (txtEmailCliente.getText().equals(null)) || (txtCpfCliente.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos Obrigatórios.");
            } else {

                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Cadastro realizado com sucesso!");
                    limparTela();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, " Não foi possível cadastrar o Cliente. "
                    + "Banco de Dados! " + e);
        }
    }
    
    
    
    
    
    /**
     * Método que ao iniciar o sistema, trabalha os campos das caixas de textos
     * e botões.
     */
    private void inicializarCadCliente(){
        txtNomeCliente.setEnabled(true);
        txtCpfCliente.setEnabled(true);
        txtEnderecoCliente.setEnabled(false);
        txtEmailCliente.setEnabled(false);
        txtFoneCliente.setEnabled(false);
        
        btnNovo.setEnabled(false);
        btnSalvar.setEnabled(false);
        btnLimparTela.setEnabled(false);
        btnPesquisarCpf.setEnabled(true);
        btnAtualizar.setEnabled(false);
        btnExcluir.setEnabled(false);
    }
    
    /**
     * Método Pesquisar Cliente pelo CPF.
     */
     private void consultarCPF() {
        String sql = "Select * From tbclientes where cpf = ?";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, txtCpfCliente.getText());
            rs = pst.executeQuery();

            if (rs.next()) {
                txtCodigoCliente.setText(rs.getString(1));
                txtNomeCliente.setText(rs.getString(2));
                txtEnderecoCliente.setText(rs.getString(3));
                txtFoneCliente.setText(rs.getString(4));
                txtEmailCliente.setText(rs.getString(5));
                txtCpfCliente.setText(rs.getString(6));
                
            } else {
                JOptionPane.showMessageDialog(null, " Cliente não Cadastrado ou , " + "\nFavor digite o número de CPF do usuário para "
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
     * Método para pesquisar clientes pelo pesquisarNomeClientecom filtro.
     */
    private void pesquisarNomeCliente() {
        String sql = "Select * From tbclientes Where nomecli Like ?";
        try {
            pst = conexao.prepareStatement(sql);
            //Passando o conteúdo da caixa de pesquisa para o Interroga.
            // atenção a "%", que é a continuação da string SQL.
            pst.setString(1, txtNomeCliente.getText() + "%");
            rs = pst.executeQuery();
            //A linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblClientes.setModel(DbUtils.resultSetToTableModel(rs));
            btnNovo.setEnabled(true);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível localizar o Usuarios!" +e);
        }
    }
    
    
    
    
     /**
     * Método Atualizar dados no Banco de Dados - (UPDATE).
     */
    private void atualizar() {
        String sql = "Update tbclientes set nomecli=?, endcli=?, fonecli=?, emailcli=?, cpf=? Where idcli=?";
        try {
            pst = conexao.prepareStatement(sql);
            //
            pst.setString(1, txtNomeCliente.getText());
            pst.setString(2, txtEnderecoCliente.getText());
            pst.setString(3, txtFoneCliente.getText());
            pst.setString(4, txtEmailCliente.getText());
            pst.setString(5, txtCpfCliente.getText());
            pst.setString(6, txtCodigoCliente.getText());

            // Código que valida os campos, se eles estiverem vazios o sistema informará que deve ser preenchidos.
            if (txtNomeCliente.getText().isEmpty() || (txtEnderecoCliente.getText().isEmpty()) || (txtFoneCliente.getText().isEmpty())
                    || (txtEmailCliente.getText().isEmpty()) || (txtCpfCliente.getText().isEmpty())) {
                JOptionPane.showMessageDialog(null, "Preencha todos os campos Obrigatórios.");
            } else {
                int adicionado = pst.executeUpdate();
                if (adicionado > 0) {
                    JOptionPane.showMessageDialog(null, "Dados Atualizados com sucesso!");
                    limparTela();
                    pesquisarNomeCliente();
                }
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível atualizar os dados do usuário. "+e);
        }
    }
    
    
    /**
     * Método para Excluir dados do Banco de Dados - (DELETE).
     */
    private void deletarClientes() {
        //A estrutura confirma a remoção(Exclusão do usuário).
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que você deseja excluir esse Cliente? ",
                "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "Delete From tbclientes Where idcli = ?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtCodigoCliente.getText());
                if (txtNomeCliente.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Favor fazer uma consulta e escolha os dados a serem excluidos.");
                } else {
                    int apagado = pst.executeUpdate();
                    if (apagado > 0) {
                        JOptionPane.showMessageDialog(null, "Cadastro Excluido com Sucesso! ");
                        limparTela();
                        pesquisarNomeCliente();
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
        int setar = tblClientes.getSelectedRow();
        txtCodigoCliente.setText(tblClientes.getModel().getValueAt(setar, 0).toString());
        txtNomeCliente.setText(tblClientes.getModel().getValueAt(setar, 1).toString());
        txtEnderecoCliente.setText(tblClientes.getModel().getValueAt(setar, 2).toString());
        txtFoneCliente.setText(tblClientes.getModel().getValueAt(setar, 3).toString());
        txtEmailCliente.setText(tblClientes.getModel().getValueAt(setar, 4).toString());  
        txtCpfCliente.setText(tblClientes.getModel().getValueAt(setar, 5).toString());

        txtNomeCliente.setEnabled(true);
        txtEnderecoCliente.setEnabled(true);
        txtFoneCliente.setEnabled(true);
        txtEmailCliente.setEnabled(true);
        txtCpfCliente.setEnabled(true);

        tblClientes.setEnabled(true);
        
        btnNovo.setEnabled(false);
        btnSalvar.setEnabled(false);
        btnLimparTela.setEnabled(true);
        btnPesquisarCpf.setEnabled(true);
        btnAtualizar.setEnabled(true);
        btnExcluir.setEnabled(true);
    }
    
    
    
    
     /**
     * Método Limpar a Tela.
     */
    public void limparTela() {
        txtCodigoCliente.setText(null);
        txtNomeCliente.setText(null);
        txtEnderecoCliente.setText(null);
        txtFoneCliente.setText(null);
        txtEmailCliente.setText(null);
        txtNomeCliente.setText(null);
        //cboTxtUsuPerfil.setSelectedItem(null);
        txtCpfCliente.setText(null);
        
        btnNovo.setEnabled(true);
        btnSalvar.setEnabled(true);

    }
    
   
   

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jLabel4 = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        jPanel_Cliente = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClientes = new javax.swing.JTable();
        jLabel5 = new javax.swing.JLabel();
        txtCodigoCliente = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtCpfCliente = new javax.swing.JTextField();
        btnPesquisarCpf = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnLimparTela = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        txtNomeCliente = new javax.swing.JTextField();
        txtEnderecoCliente = new javax.swing.JTextField();
        txtFoneCliente = new javax.swing.JTextField();
        txtEmailCliente = new javax.swing.JTextField();

        jLabel4.setText("Cód :");

        txtCodigo.setEditable(false);
        txtCodigo.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Clientes");
        setResizable(false);

        jPanel_Cliente.setBackground(new java.awt.Color(255, 255, 255));

        jPanel3.setBackground(new java.awt.Color(102, 102, 255));

        jLabel3.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Cadastro de Clientes ");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(298, 298, 298)
                .addComponent(jLabel3)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel3)
                .addContainerGap(29, Short.MAX_VALUE))
        );

        tblClientes = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblClientes.setFont(new java.awt.Font("Arial", 0, 12)); // NOI18N
        tblClientes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "idCli", "nomdeCli", "endCli", "foneCli", "emailCli", "cpf"
            }
        ));
        tblClientes.setFocusable(false);
        tblClientes.getTableHeader().setResizingAllowed(false);
        tblClientes.getTableHeader().setReorderingAllowed(false);
        tblClientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClientesMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClientes);

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setText("Cód  :");

        txtCodigoCliente.setEditable(false);
        txtCodigoCliente.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setText("Nome  :");

        jLabel9.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel9.setText("Endereço :");

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("Fone :");

        jLabel10.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel10.setText("Email :");

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setText("Cpf :");

        txtCpfCliente.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtCpfCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtCpfClienteKeyTyped(evt);
            }
        });

        btnPesquisarCpf.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        btnPesquisarCpf.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa2_2.png"))); // NOI18N
        btnPesquisarCpf.setToolTipText("Pesquisar pelo CPF");
        btnPesquisarCpf.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPesquisarCpf.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarCpfActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(51, 102, 255));
        jLabel12.setText("Consultar Cliente pelo nome :");

        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/novo.jpg"))); // NOI18N
        btnNovo.setToolTipText("Novo dados para se adicionados");
        btnNovo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked.png"))); // NOI18N
        btnSalvar.setToolTipText("Salvar dados");
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
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

        btnAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/upgrade_2.png"))); // NOI18N
        btnAtualizar.setToolTipText("Atualizar Dados");
        btnAtualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
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

        txtNomeCliente.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtNomeCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtNomeClienteKeyReleased(evt);
            }
        });

        txtEnderecoCliente.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        txtFoneCliente.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtFoneCliente.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txtFoneClienteKeyTyped(evt);
            }
        });

        txtEmailCliente.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        javax.swing.GroupLayout jPanel_ClienteLayout = new javax.swing.GroupLayout(jPanel_Cliente);
        jPanel_Cliente.setLayout(jPanel_ClienteLayout);
        jPanel_ClienteLayout.setHorizontalGroup(
            jPanel_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ClienteLayout.createSequentialGroup()
                .addGroup(jPanel_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_ClienteLayout.createSequentialGroup()
                        .addComponent(jLabel10)
                        .addGap(18, 18, 18)
                        .addComponent(txtEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_ClienteLayout.createSequentialGroup()
                        .addGap(48, 48, 48)
                        .addComponent(jLabel7)
                        .addGap(18, 18, 18)
                        .addComponent(txtFoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(548, 548, 548))
            .addGroup(jPanel_ClienteLayout.createSequentialGroup()
                .addGroup(jPanel_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ClienteLayout.createSequentialGroup()
                        .addGap(12, 12, 12)
                        .addGroup(jPanel_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 829, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addGroup(jPanel_ClienteLayout.createSequentialGroup()
                                .addGroup(jPanel_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel9)
                                    .addComponent(jLabel5, javax.swing.GroupLayout.Alignment.TRAILING))
                                .addGap(18, 18, 18)
                                .addGroup(jPanel_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 48, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txtEnderecoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(27, 27, 27)
                                .addGroup(jPanel_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel_ClienteLayout.createSequentialGroup()
                                        .addComponent(jLabel8)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addGroup(jPanel_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(jLabel12)
                                            .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 305, javax.swing.GroupLayout.PREFERRED_SIZE)))
                                    .addGroup(jPanel_ClienteLayout.createSequentialGroup()
                                        .addGap(24, 24, 24)
                                        .addComponent(jLabel6)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(txtCpfCliente, javax.swing.GroupLayout.PREFERRED_SIZE, 205, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                        .addComponent(btnPesquisarCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 93, javax.swing.GroupLayout.PREFERRED_SIZE))))))
                    .addGroup(jPanel_ClienteLayout.createSequentialGroup()
                        .addGap(117, 117, 117)
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnLimparTela, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 119, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel_ClienteLayout.setVerticalGroup(
            jPanel_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_ClienteLayout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel12)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel8)
                    .addComponent(txtNomeCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCodigoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel5))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(txtCpfCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel6)
                    .addComponent(btnPesquisarCpf)
                    .addComponent(txtEnderecoCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7)
                    .addComponent(txtFoneCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtEmailCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_ClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnLimparTela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnAtualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(btnExcluir))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 156, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, 845, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel_Cliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(863, 558));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txtCpfClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtCpfClienteKeyTyped
        // TODO add your handling code here:
         int numerosCaracteres = 11;
        if (txtCpfCliente.getText().length()>= numerosCaracteres) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Pode-se digitar somente 11Caracteres e sem espaços, sem pontos!");
        }
    }//GEN-LAST:event_txtCpfClienteKeyTyped

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // TODO add your handling code here:
        controleBotaoNovo();
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        // TODO add your handling code here:
         adicionarClientes();
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnLimparTelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparTelaActionPerformed
        // TODO add your handling code here:
        controleBotaoLimpar();
    }//GEN-LAST:event_btnLimparTelaActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        // TODO add your handling code here:
        atualizar();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
        // TODO add your handling code here:
        deletarClientes();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void txtNomeClienteKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtNomeClienteKeyReleased
        // TODO add your handling code here:
        pesquisarNomeCliente();
    }//GEN-LAST:event_txtNomeClienteKeyReleased

    private void btnPesquisarCpfActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarCpfActionPerformed
        // TODO add your handling code here:
        consultarCPF();
        controlarBotaobtnPesquisarCpf();
    }//GEN-LAST:event_btnPesquisarCpfActionPerformed

    private void txtFoneClienteKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtFoneClienteKeyTyped
        // TODO add your handling code here:
        // O código controla a quantidade de caracteres a serem digitados.
        int numerosCaracteres = 11;
        if (txtFoneCliente.getText().length()>= numerosCaracteres) {
            evt.consume();
            JOptionPane.showMessageDialog(null, "Pode-se digitar somente 11Caracteres e sem espaços, sem pontos!");
        }
    }//GEN-LAST:event_txtFoneClienteKeyTyped

    private void tblClientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClientesMouseClicked
        // TODO add your handling code here:
        setarCampos();
    }//GEN-LAST:event_tblClientesMouseClicked

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
            java.util.logging.Logger.getLogger(TelaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaCliente().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnLimparTela;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisarCpf;
    private javax.swing.JButton btnSalvar;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel_Cliente;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable tblClientes;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtCodigoCliente;
    private javax.swing.JTextField txtCpfCliente;
    private javax.swing.JTextField txtEmailCliente;
    private javax.swing.JTextField txtEnderecoCliente;
    private javax.swing.JTextField txtFoneCliente;
    private javax.swing.JTextField txtNomeCliente;
    // End of variables declaration//GEN-END:variables

    
    /**
     * Método que controla a tabela Clientes e chama os métodos ConsultarCPF
     * e pesquisarNomeCliente
     */
    public void controlarBotaobtnPesquisarCpf(){
        consultarCPF();
        pesquisarNomeCliente();
        tblClientes.setEnabled(true);
     }
    
    /***
     * Método de controle do botão novo Cadastro.
     */
    private void controleBotaoNovo(){
        limparTela();
        txtNomeCliente.setEnabled(true);
        txtEnderecoCliente.setEnabled(true);
        txtFoneCliente.setEnabled(true);
        txtEmailCliente.setEnabled(true);
        txtCpfCliente.setEnabled(true);
        
        btnNovo.setEnabled(false);
        btnSalvar.setEnabled(true);
        btnLimparTela.setEnabled(true);
        btnPesquisarCpf.setEnabled(true);
        btnExcluir.setEnabled(true);
    }
    
    
    /***
     * Método de controle do botão limpar campos.
     */
       private void controleBotaoLimpar(){
        limparTela();
        txtNomeCliente.setEnabled(true);
        txtEnderecoCliente.setEnabled(false);
        txtFoneCliente.setEnabled(false);
        txtEmailCliente.setEnabled(false);
        txtCpfCliente.setEnabled(true);
        
        btnNovo.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnLimparTela.setEnabled(false);
        btnAtualizar.setEnabled(false);
        btnPesquisarCpf.setEnabled(true);
        btnExcluir.setEnabled(false);
       }
    
    
    
    
}
