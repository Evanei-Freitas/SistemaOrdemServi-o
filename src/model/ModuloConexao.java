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
package model;

import java.sql.*;
import javax.swing.JOptionPane;

/**
 * Conexão com o banco de Dados
 *
 * @author Engenheiro de Software Evanei Freitas - (Programador : Evanei
 * Freitas)
 * @version 1.1
 */
//Classe ModuloConexao.
public class ModuloConexao {

    /**
     *
     * @return conexao Método responsável pela conexão com o banco.
     * @throws SQLException Método responsável por tratar a conexao com banco,
     * informando se ocoreu tudo bem ou não na conexão.
     *
     */
    public static Connection conector() throws SQLException, ClassNotFoundException {
        java.sql.Connection conexao = null;
        String driver = "com.mysql.jdbc.Driver";
        String url = "jdbc:mysql://localhost:3306/dbinfox?characterEncoding=utf-8";
        String user = "root";
        String password = "";

        try {
            Class.forName(driver);
            conexao = DriverManager.getConnection(url, user, password);
            return conexao;
        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(null, "Acesso negado para usuario \n"
                    + "Verifique se a senha de Usuário correta");
        } catch (Exception e2) {
            JOptionPane.showMessageDialog(null, " Erro!  :  a conexão com  Banco de Dados não esta funcionando \n"
                    + " O driver não recebeu nenhum pacote do servidor \n");
            return null;
        }
        return null;

    }
}
