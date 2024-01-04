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
import java.util.HashMap;
import javax.swing.ImageIcon;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import model.ModuloConexao;
import net.proteanit.sql.DbUtils;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.view.JasperViewer;



/**
 * @version 1.1
 * @author Engenheiro de Software e Programador - Evanei Freitas
 * Connection conexão com o banco de dados
 * PreparedStatement Manipula instruções SQL
 * ResultSet trás os resultados da manipulação das instruções SQL
 */
public class TelaOS extends javax.swing.JFrame {
    
    Connection conexao = null;
    PreparedStatement pst = null;
    ResultSet rs = null;
    
    private String tipo;
    
    
    
    /**
     * Creates new form TelaOS - Método construtor da classe
     * SQLException informa se houver algum erro de SQLException, na
     * conexão com o banco de dados.
     * ClassNotFoundException informa se houver algum erro de 
     * ClassNotFoundException, na conexão com o banco de dados.
     */
    public TelaOS() {
        initComponents();
        ImageIcon imageTitulodaJanela = new ImageIcon("C:/xampp/htdocs/prjinfoTeste/src/img/pc_33X33.png");
        setIconImage(imageTitulodaJanela.getImage());
        //txtValorTotal.setDocument(new SoNumeros());
        try {
            conexao = ModuloConexao.conector();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null,"Erro de : SQLException "+ ex);
        } catch (ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null,"Erro de : ClassNotFoundException"+ ex);
        }
        
    }
    
    
     /**
     * Método para pesquisar clientes pelo pesquisarNomeClientecom filtro.
     */
    private void pesquisarNomeCliente() {
        String sql = "select idcli as Id, nomecli as Nome, fonecli as Fone, "
                + "emailcli as Email from tbclientes Where nomecli Like ?;";
        try {
            pst = conexao.prepareStatement(sql);
            //Passando o conteúdo da caixa de pesquisa para o Interroga.
            // atenção a "%", que é a continuação da string SQL.
            pst.setString(1, txtClientesOS.getText() + "%");
            rs = pst.executeQuery();
            //A linha abaixo usa a biblioteca rs2xml.jar para preencher a tabela
            tblClienteOS.setModel(DbUtils.resultSetToTableModel(rs));

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível localizar o Cliente.!" +e);
        }
    }
    
    
      /**
       * Método para setar os campos do formulário com o conteúdo da tabela.
       */
    private void setarCampos() {
        int setar = tblClienteOS.getSelectedRow();
        txtIdClientesOs.setText(tblClienteOS.getModel().getValueAt(setar, 0).toString());
        txtClientesOS.setText(tblClienteOS.getModel().getValueAt(setar, 1).toString());
    }
    
    
    
    /**
     * Método para cadastrar uma (OS) - Ordem de Serviço.
     */
    private void emitir_Os(){
        String sql = "insert into tbos(tipo, situacao, equipamento, defeito, "
                + "servico, tecnico, valor, idcli) values(?,?,?,?,?,?,?,?)";
        try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, jComboBox_OsSit.getSelectedItem().toString());
            pst.setString(3, txtEquipamento.getText());
            pst.setString(4, txtDefeito.getText());
            pst.setString(5, txtServico.getText());
            pst.setString(6, txtTecnico.getText());
            pst.setString(7, txtValorTotal.getText().replace(",", "."));
            pst.setString(8, txtIdClientesOs.getText());
            
            //Validação dos Campos Obrigatorios.
            if (txtClientesOS.getText().isEmpty() || (txtDefeito.getText().isEmpty() ||
                    (txtEquipamento.getText().isEmpty()))) {
             JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");   
            } else {
                int adicionado = pst.executeUpdate();
            if(adicionado > 0){
                JOptionPane.showMessageDialog(null, "(OS) - Ordem de Serviço emitida com sucesso!");
                // (recuperarOS()) - recuperar o NºOS
                recuperarOS();
                limparTestosBotoesOS();
            }
          }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível cadastrar OS.!" +e);
        }
    }
    
    
    /**
     * Método para pesquisar OS.
     */
    private void pesquisarOS(){
        String num_OS = JOptionPane.showInputDialog("Número da OS :");
        //String sql = "Select * From tbos Where os = " + num_OS;
        String sql = "Select os,date_format(data_os,'%d/%m/%Y - %H:%i'),tipo,situacao,equipamento,"
                + "defeito,servico,tecnico,valor,idcli from tbos Where os=" + num_OS;
        try {
           pst = conexao.prepareStatement(sql);
           rs = pst.executeQuery();
            if (rs.next()) {
               txtNumeroOS.setText(rs.getString(1));
               txtData.setText(rs.getString(2));
               //Setando os RadioButons.
               String rbtTipo = rs.getString(3);   
                if (rbtTipo.equals("OS")) {
                  rbt_Os.setSelected(true);
                  tipo = "OS";
                } else {
                   rbt_Orc.setSelected(true);
                   tipo = "Orçamento";
                }
               txtTipoServico.setText(rs.getString(3));
               jComboBox_OsSit.setSelectedItem(rs.getString(4));
               txtEquipamento.setText(rs.getString(5));
               txtDefeito.setText(rs.getString(6));
               txtServico.setText(rs.getString(7));
               txtTecnico.setText(rs.getString(8));
               txtValorTotal.setText(rs.getString(9));
               txtIdClientesOs.setText(rs.getString(10));
               
               
            } else {
                JOptionPane.showMessageDialog(null, "OS não encontrada.!");
            }
        } catch (SQLSyntaxErrorException e) {
            JOptionPane.showMessageDialog(null, "OS Inválida.!");
        }catch(Exception e2){
            JOptionPane.showMessageDialog(null, e2);
        }
    }
    
 
    
  
    /**
     * Método para alterar uma (OS) - Ordem de Serviços.
     */
    private void alterarOS(){
        String sql = "Update tbos set tipo=?, situacao=?, equipamento=?, defeito=?,"
                + "servico=?, tecnico=?, valor=? Where os=?";
       try {
            pst = conexao.prepareStatement(sql);
            pst.setString(1, tipo);
            pst.setString(2, jComboBox_OsSit.getSelectedItem().toString());
            pst.setString(3, txtEquipamento.getText());
            pst.setString(4, txtDefeito.getText());
            pst.setString(5, txtServico.getText());
            pst.setString(6, txtTecnico.getText());
            pst.setString(7, txtValorTotal.getText().replace(",", "."));
            pst.setString(8, txtNumeroOS.getText());
            
            //Validação dos Campos Obrigatorios.
            if (txtClientesOS.getText().isEmpty() || (txtDefeito.getText().isEmpty() ||
                    (txtEquipamento.getText().isEmpty()))) {
             JOptionPane.showMessageDialog(null, "Preencha os campos obrigatórios");   
            } else {
                int adicionado = pst.executeUpdate();
            if(adicionado > 0){
                JOptionPane.showMessageDialog(null, "(OS) - Ordem de Serviço alterada com sucesso!"); 
                limparTestosBotoesOS();
            }
          }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Não foi possível alterar OS.!" +e);
        }
    }
    
    
    
    /**
     * Método para excluir OS.
     */
    private void excluirOS(){
         //A estrutura confirma a remoção(Exclusão do usuário).
        int confirma = JOptionPane.showConfirmDialog(null, "Tem certeza que você deseja excluir essa OS? ",
                "Atenção", JOptionPane.YES_NO_OPTION);
        if (confirma == JOptionPane.YES_OPTION) {
            String sql = "Delete From tbos Where os=?";
            try {
                pst = conexao.prepareStatement(sql);
                pst.setString(1, txtNumeroOS.getText());
                if (txtIdClientesOs.getText().isEmpty()) {
                    JOptionPane.showMessageDialog(null, "Favor fazer uma consulta e escolha os dados a serem excluidos.");
                } else {
                    int excluido = pst.executeUpdate();
                    if (excluido > 0) {
                        JOptionPane.showMessageDialog(null, "Ordem de Serviço Excluido com Sucesso! ");
                         limparTestosBotoesOS();
                        
                    }

                }

            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, "Erro : \nNão foi possível Excluir os Dados!\n "
                        + "Voce deve primeiro consultar o usuário a ser Excluido!" + e);
            }
        }

    }
    
    
    /**
     * Método para imprimir uma OS.
     */
    private void imprimirOS(){
        int confirma = JOptionPane.showConfirmDialog(null, "Confirma a impressão desta OS? ", ""
                + "Atenção!", JOptionPane.YES_NO_OPTION);
        if(confirma == JOptionPane.YES_OPTION){
            //Emitindo o Relatório com o FrameWork JasperReports.
            try {
                //Usando a classe hashMap para criar um filtro para escolher qual OS de ver impressa.
                HashMap filtro = new HashMap();
                filtro.put("os",Integer.parseInt(txtNumeroOS.getText()));
                // Usando a Classe JasperPrint para preparar a Impressão de um Relatórioclientes.
                JasperPrint print = JasperFillManager.fillReport("C:/xampp/htdocs/prjinfoTeste/reports/os.jasper",filtro,conexao);
                // A linha abaixo exibe o relatório.
                JasperViewer.viewReport(print,false);
            } catch (Exception e) {
                JOptionPane.showMessageDialog(null, e);
            }
        }
    }
    
    
    
    /**
     * Método para recuperar o NºOS
     */
    private void recuperarOS(){
        String sql = "Select max(os) from tbos";
        try {
             pst = conexao.prepareStatement(sql);
             rs = pst.executeQuery();
             if(rs.next()){
                 txtNumeroOS.setText(rs.getString(1));
             }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }
    
    
    
    
    /**
     * Metodo que limpa textos e botões do metodo emitirOS
     */
    private void limparTestosBotoesOS(){
        btnNovo.setEnabled(true);
        btnSalvar.setEnabled(false);
        btnImprimir.setEnabled(false);
        btnAtualizar.setEnabled(false);
        btnPesquisarOS.setEnabled(true);
       
       
    }
    
    /**
     * Método controle do botão Limpar.
     */
    private void controleBotaoLimpar(){
        txtNumeroOS.setText(null);
        txtClientesOS.setText(null);
        txtData.setText(null);
        txtEquipamento.setText(null);
        txtDefeito.setText(null);
        txtServico.setText(null);
        txtTecnico.setText(null);
        //txtValorTotal.setText(null);
        ((DefaultTableModel)tblClienteOS.getModel()).setRowCount(0);
        
        btnNovo.setEnabled(true);
        btnSalvar.setEnabled(true);
        btnLimparTela.setEnabled(true);
        btnAtualizar.setEnabled(false);
        btnExcluir.setEnabled(false);
        btnPesquisarOS.setEnabled(true);
        btnImprimir.setEnabled(true);
    }
    
    
    /**
     * Método controle botão PesquisarOS.
     */
    private void controleBotaoPesquisarOS(){
        txtEquipamento.setEditable(true);
        txtDefeito.setEditable(true);
        txtServico.setEditable(true);
        txtTecnico.setEditable(true);
        txtValorTotal.setEditable(true);
        
        ((DefaultTableModel)tblClienteOS.getModel()).setRowCount(0);
        
        btnNovo.setEnabled(false);
        btnSalvar.setEnabled(false);
        btnLimparTela.setEnabled(true);
        btnAtualizar.setEnabled(true);
        btnExcluir.setEnabled(true);
        btnPesquisarOS.setEnabled(true);
        btnImprimir.setEnabled(true);
    }
    
    
    
    
    
    
    
    

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        buttonGroup1 = new javax.swing.ButtonGroup();
        jButton1 = new javax.swing.JButton();
        jPanel_OS = new javax.swing.JPanel();
        jPanel1 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jPanel2 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        txtNumeroOS = new javax.swing.JTextField();
        txtData = new javax.swing.JTextField();
        rbt_Orc = new javax.swing.JRadioButton();
        rbt_Os = new javax.swing.JRadioButton();
        jLabel4 = new javax.swing.JLabel();
        jComboBox_OsSit = new javax.swing.JComboBox<>();
        jPanel3 = new javax.swing.JPanel();
        txtClientesOS = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txtIdClientesOs = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblClienteOS = new javax.swing.JTable();
        jLabel7 = new javax.swing.JLabel();
        txtEquipamento = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        txtDefeito = new javax.swing.JTextField();
        jLabel9 = new javax.swing.JLabel();
        txtServico = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        txtTecnico = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        txtValorTotal = new javax.swing.JTextField();
        btnNovo = new javax.swing.JButton();
        btnSalvar = new javax.swing.JButton();
        btnLimparTela = new javax.swing.JButton();
        btnAtualizar = new javax.swing.JButton();
        btnExcluir = new javax.swing.JButton();
        btnImprimir = new javax.swing.JButton();
        btnPesquisarOS = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        txtTipoServico = new javax.swing.JTextField();

        jButton1.setText("jButton1");

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Ordem de Serviços");
        setLocationByPlatform(true);
        setResizable(false);
        addWindowListener(new java.awt.event.WindowAdapter() {
            public void windowOpened(java.awt.event.WindowEvent evt) {
                formWindowOpened(evt);
            }
        });

        jPanel_OS.setBackground(new java.awt.Color(255, 255, 255));
        jPanel_OS.setPreferredSize(new java.awt.Dimension(957, 549));

        jPanel1.setBackground(new java.awt.Color(102, 102, 255));

        jLabel1.setFont(new java.awt.Font("Arial", 0, 24)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Cadastro de Ordem de Serviços ");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(301, 301, 301)
                .addComponent(jLabel1)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addComponent(jLabel1)
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel2.setBackground(new java.awt.Color(102, 102, 255));
        jPanel2.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel2.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Nº OS  :");

        jLabel3.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("Data  :");

        txtNumeroOS.setEditable(false);
        txtNumeroOS.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N

        txtData.setEditable(false);
        txtData.setBackground(new java.awt.Color(204, 204, 204));
        txtData.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtData.setForeground(new java.awt.Color(102, 102, 102));

        buttonGroup1.add(rbt_Orc);
        rbt_Orc.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rbt_Orc.setText("Orçamento");
        rbt_Orc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbt_OrcActionPerformed(evt);
            }
        });

        buttonGroup1.add(rbt_Os);
        rbt_Os.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        rbt_Os.setText("OS");
        rbt_Os.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                rbt_OsActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(txtNumeroOS)
                            .addComponent(jLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, 143, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel3)))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addComponent(rbt_Orc)
                        .addGap(18, 18, 18)
                        .addComponent(rbt_Os)
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtNumeroOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtData, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(rbt_Orc)
                    .addComponent(rbt_Os))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jLabel4.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jLabel4.setText("Situação  :");

        jComboBox_OsSit.setFont(new java.awt.Font("Arial", 0, 14)); // NOI18N
        jComboBox_OsSit.setForeground(new java.awt.Color(51, 51, 51));
        jComboBox_OsSit.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Na bancanda", "Entrega OK", "Serviço Feito", "Pronto para entregar", "Orçamento Reprovado", "Orçamento Aprovado", "Aguardando Aprovação", "Aguardando Peças", "Abandonado pelo Cliente", "Retornou", "Sem conserto", "Mechido pelo cliente", " " }));

        jPanel3.setBackground(new java.awt.Color(102, 102, 255));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cliente :", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 13), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        txtClientesOS.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtClientesOS.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txtClientesOSKeyReleased(evt);
            }
        });

        jLabel5.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel5.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa.png"))); // NOI18N

        jLabel6.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("* id");

        txtIdClientesOs.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtIdClientesOs.setEnabled(false);

        tblClienteOS = new javax.swing.JTable(){
            public boolean isCellEditable(int rowIndex, int colIndex){
                return false;
            }
        };
        tblClienteOS.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        tblClienteOS.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Id", "Nome", "Fone", "Email"
            }
        ));
        tblClienteOS.setFocusable(false);
        tblClienteOS.getTableHeader().setReorderingAllowed(false);
        tblClienteOS.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tblClienteOSMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tblClienteOS);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txtClientesOS, javax.swing.GroupLayout.PREFERRED_SIZE, 272, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel5)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel6)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtIdClientesOs)
                .addContainerGap())
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 596, Short.MAX_VALUE)
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtClientesOS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel5))
                    .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel6)
                        .addComponent(txtIdClientesOs, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 87, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        jLabel7.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel7.setText("* Equipamento  :");

        txtEquipamento.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel8.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel8.setText("* Defeito  :");

        txtDefeito.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel9.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel9.setText("Serviço  :");

        txtServico.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N

        jLabel10.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel10.setText("Técnico :");

        txtTecnico.setBackground(new java.awt.Color(204, 204, 204));
        txtTecnico.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtTecnico.setForeground(new java.awt.Color(153, 153, 153));

        jLabel11.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel11.setText("Valor Total  :");

        txtValorTotal.setBackground(new java.awt.Color(204, 204, 204));
        txtValorTotal.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtValorTotal.setForeground(new java.awt.Color(153, 51, 0));
        txtValorTotal.setText("0");

        btnNovo.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        btnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/novo.jpg"))); // NOI18N
        btnNovo.setToolTipText("Novo dados para se adicionados");
        btnNovo.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnNovoActionPerformed(evt);
            }
        });

        btnSalvar.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        btnSalvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/checked.png"))); // NOI18N
        btnSalvar.setToolTipText("Salvar dados");
        btnSalvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnSalvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnSalvarActionPerformed(evt);
            }
        });

        btnLimparTela.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        btnLimparTela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/cancel_2.jpg"))); // NOI18N
        btnLimparTela.setToolTipText("Cancelar e Limpar a tela");
        btnLimparTela.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnLimparTela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnLimparTelaActionPerformed(evt);
            }
        });

        btnAtualizar.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        btnAtualizar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/upgrade_2.png"))); // NOI18N
        btnAtualizar.setToolTipText("Atualizar Dados");
        btnAtualizar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnAtualizar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAtualizarActionPerformed(evt);
            }
        });

        btnExcluir.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        btnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Excluir.jpg"))); // NOI18N
        btnExcluir.setToolTipText("Excluir dados");
        btnExcluir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnExcluirActionPerformed(evt);
            }
        });

        btnImprimir.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        btnImprimir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/Printer_Scanner2_.png"))); // NOI18N
        btnImprimir.setToolTipText("Imprimir");
        btnImprimir.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnImprimir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnImprimirActionPerformed(evt);
            }
        });

        btnPesquisarOS.setBackground(new java.awt.Color(255, 255, 255));
        btnPesquisarOS.setFont(new java.awt.Font("Arial", 0, 13)); // NOI18N
        btnPesquisarOS.setIcon(new javax.swing.ImageIcon(getClass().getResource("/img/lupa2.png"))); // NOI18N
        btnPesquisarOS.setToolTipText("Pesquisar OS por Nº da OS");
        btnPesquisarOS.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPesquisarOS.setPreferredSize(new java.awt.Dimension(59, 35));
        btnPesquisarOS.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarOSActionPerformed(evt);
            }
        });

        jLabel12.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        jLabel12.setText("Tipo  :");

        txtTipoServico.setEditable(false);
        txtTipoServico.setBackground(new java.awt.Color(204, 204, 204));
        txtTipoServico.setFont(new java.awt.Font("Arial", 0, 18)); // NOI18N
        txtTipoServico.setForeground(new java.awt.Color(153, 51, 0));

        javax.swing.GroupLayout jPanel_OSLayout = new javax.swing.GroupLayout(jPanel_OS);
        jPanel_OS.setLayout(jPanel_OSLayout);
        jPanel_OSLayout.setHorizontalGroup(
            jPanel_OSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel_OSLayout.createSequentialGroup()
                .addGroup(jPanel_OSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_OSLayout.createSequentialGroup()
                        .addContainerGap(175, Short.MAX_VALUE)
                        .addComponent(btnSalvar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnLimparTela, javax.swing.GroupLayout.PREFERRED_SIZE, 126, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btnAtualizar, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 124, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(1, 1, 1)
                        .addComponent(btnPesquisarOS, javax.swing.GroupLayout.PREFERRED_SIZE, 123, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(2, 2, 2)
                        .addComponent(btnImprimir, javax.swing.GroupLayout.PREFERRED_SIZE, 122, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_OSLayout.createSequentialGroup()
                        .addGap(103, 103, 103)
                        .addGroup(jPanel_OSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel7)
                            .addComponent(jLabel8)
                            .addComponent(jLabel9)
                            .addComponent(jLabel11))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel_OSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel_OSLayout.createSequentialGroup()
                                .addComponent(txtValorTotal)
                                .addGap(61, 61, 61)
                                .addComponent(jLabel10)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txtTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, 238, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txtServico, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(txtDefeito)
                            .addGroup(jPanel_OSLayout.createSequentialGroup()
                                .addComponent(txtEquipamento)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jLabel12)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(txtTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, 162, javax.swing.GroupLayout.PREFERRED_SIZE))))
                    .addGroup(jPanel_OSLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel_OSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel_OSLayout.createSequentialGroup()
                                .addGap(0, 0, Short.MAX_VALUE)
                                .addComponent(btnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(153, 153, 153))
                            .addGroup(jPanel_OSLayout.createSequentialGroup()
                                .addComponent(jLabel4)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBox_OsSit, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel_OSLayout.setVerticalGroup(
            jPanel_OSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel_OSLayout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_OSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_OSLayout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel_OSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel4)
                            .addComponent(jComboBox_OsSit, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(6, 6, 6))
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel_OSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel_OSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jLabel7)
                        .addComponent(txtEquipamento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel_OSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtTipoServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(jLabel12)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_OSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel8, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txtDefeito, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel_OSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtServico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel9, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel_OSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel10)
                    .addComponent(txtTecnico, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11)
                    .addComponent(txtValorTotal, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(44, 44, 44)
                .addGroup(jPanel_OSLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(btnSalvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnLimparTela, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnAtualizar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnNovo, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnExcluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnImprimir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btnPesquisarOS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(206, 206, 206))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel_OS, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel_OS, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(999, 627));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnNovoActionPerformed
        // Código do botão novo cadastro.
        controleBotaoLimpar();
       
    }//GEN-LAST:event_btnNovoActionPerformed

    private void btnSalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnSalvarActionPerformed
        //Código para cadastrar OS.
        emitir_Os();
        
    }//GEN-LAST:event_btnSalvarActionPerformed

    private void btnLimparTelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnLimparTelaActionPerformed
        // Código do botão Limpar a Tela.
        controleBotaoLimpar();
    }//GEN-LAST:event_btnLimparTelaActionPerformed

    private void btnAtualizarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAtualizarActionPerformed
        // Código do botão Atualizar dados.
        alterarOS();
        controleBotaoLimpar();
    }//GEN-LAST:event_btnAtualizarActionPerformed

    private void btnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnExcluirActionPerformed
      //Código para excluir uma OS.
      excluirOS();
      controleBotaoLimpar();
    }//GEN-LAST:event_btnExcluirActionPerformed

    private void btnImprimirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnImprimirActionPerformed
        // Aqui chamo o método imprimir OS
        imprimirOS();
    }//GEN-LAST:event_btnImprimirActionPerformed

    private void txtClientesOSKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txtClientesOSKeyReleased
        //Aqui o código pesquisa o cliente na tabela de OS pelo Nome do Cliente digitado
        pesquisarNomeCliente();
    }//GEN-LAST:event_txtClientesOSKeyReleased

    private void tblClienteOSMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tblClienteOSMouseClicked
        // TODO add your handling code here:
        setarCampos();
    }//GEN-LAST:event_tblClienteOSMouseClicked

    private void rbt_OrcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbt_OrcActionPerformed
        // Atribuindo um texto a variável tipo se selecionado.
           tipo = "Orçamento";
        
    }//GEN-LAST:event_rbt_OrcActionPerformed

    private void rbt_OsActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_rbt_OsActionPerformed
           // Atribuindo um texto a variável tipo se selecionado.
           tipo = "OS";
    }//GEN-LAST:event_rbt_OsActionPerformed

    private void formWindowOpened(java.awt.event.WindowEvent evt) {//GEN-FIRST:event_formWindowOpened
        //Ao abrir o forme marcar o radio butom como Orçamento.
        rbt_Orc.setSelected(true);
        tipo = "Orçamento";
        
    }//GEN-LAST:event_formWindowOpened

    private void btnPesquisarOSActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarOSActionPerformed
        //Abaixo o código para pesquisar OS
        pesquisarOS();
        controleBotaoPesquisarOS();
    }//GEN-LAST:event_btnPesquisarOSActionPerformed

    
    
    
    
    
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
            java.util.logging.Logger.getLogger(TelaOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TelaOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TelaOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TelaOS.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TelaOS().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton btnAtualizar;
    private javax.swing.JButton btnExcluir;
    private javax.swing.JButton btnImprimir;
    private javax.swing.JButton btnLimparTela;
    private javax.swing.JButton btnNovo;
    private javax.swing.JButton btnPesquisarOS;
    private javax.swing.JButton btnSalvar;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton1;
    private javax.swing.JComboBox<String> jComboBox_OsSit;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel_OS;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rbt_Orc;
    private javax.swing.JRadioButton rbt_Os;
    private javax.swing.JTable tblClienteOS;
    private javax.swing.JTextField txtClientesOS;
    private javax.swing.JTextField txtData;
    private javax.swing.JTextField txtDefeito;
    private javax.swing.JTextField txtEquipamento;
    private javax.swing.JTextField txtIdClientesOs;
    private javax.swing.JTextField txtNumeroOS;
    private javax.swing.JTextField txtServico;
    private javax.swing.JTextField txtTecnico;
    private javax.swing.JTextField txtTipoServico;
    private javax.swing.JTextField txtValorTotal;
    // End of variables declaration//GEN-END:variables
}
