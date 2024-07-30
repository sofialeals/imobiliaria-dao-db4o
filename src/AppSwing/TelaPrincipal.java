package AppSwing;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.time.LocalDate;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenuBar;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import Classes.Morador;
import RegrasNegocio.Fachada;

public class TelaPrincipal {

	private JFrame menuPrincipal;
	private JTextField inputNome;
	private JTextField inputCpf;
	private JTextField inputCpfAcordo;
	private JTextField inputNomeCond;
	private JTextField inputEndCond;
	private JTextField inputIdCond;
	private JTextField inputIdCC;
	private JTextField inputCpfCC;
	private JTextField inputValorAluguel;
	private JTextField inputIdEC;
	private JTextField inputCpfEC;

	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					TelaPrincipal window = new TelaPrincipal();
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public TelaPrincipal() {
		initialize();
	}

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		String[] meses = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho", "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};
		
		menuPrincipal = new JFrame();
		JLabel lblMesRef = new JLabel("");
		JTextArea listagemMoradores = new JTextArea();
		
		menuPrincipal.addWindowListener(new WindowAdapter() {
			@Override
			public void windowOpened(WindowEvent e) {
				Fachada.inicializar();
				int mesAtual = LocalDate.now().getMonthValue();
				lblMesRef.setText(meses[mesAtual-1]);
				
				listagemMoradores.setText(Fachada.exibirMoradores());
			}
			@Override
			public void windowClosing(WindowEvent e) {
				Fachada.finalizar();
			}
		});
		
		menuPrincipal.setIconImage(Toolkit.getDefaultToolkit().getImage(TelaPrincipal.class.getResource("/Arquivos/apartamento.png")));
		menuPrincipal.setTitle("Imobiliária Silva");
		menuPrincipal.setBounds(100, 100, 654, 550);
		menuPrincipal.setResizable(false);
		menuPrincipal.setLocationRelativeTo(null);
		menuPrincipal.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		menuPrincipal.getContentPane().setLayout(null);
		menuPrincipal.setVisible(true);
		
		JPanel moradores = new JPanel();
		moradores.setBackground(new Color(225, 225, 225));
		moradores.setBounds(0, 0, 638, 480);
		menuPrincipal.getContentPane().add(moradores);
		moradores.setLayout(null);
		moradores.setVisible(true);
		
		JLabel lblMoradores = new JLabel("MORADORES");
		lblMoradores.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 25));
		lblMoradores.setHorizontalAlignment(SwingConstants.CENTER);
		lblMoradores.setBounds(250, 11, 138, 23);
		moradores.add(lblMoradores);
		
		JLabel lblCadastrar = new JLabel("CADASTRAR MORADOR");
		lblCadastrar.setHorizontalAlignment(SwingConstants.LEFT);
		lblCadastrar.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 18));
		lblCadastrar.setBounds(23, 57, 183, 14);
		moradores.add(lblCadastrar);
		
		inputNome = new JTextField();
		inputNome.setToolTipText("Nome do morador.");
		inputNome.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 15));
		inputNome.setBounds(23, 99, 197, 34);
		moradores.add(inputNome);
		inputNome.setColumns(10);
		
		JLabel lblNome = new JLabel("NOME:");
		lblNome.setHorizontalAlignment(SwingConstants.LEFT);
		lblNome.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		lblNome.setBounds(23, 82, 159, 14);
		moradores.add(lblNome);
		
		inputCpf = new JTextField();
		inputCpf.setToolTipText("CPF do morador.");
		inputCpf.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 15));
		inputCpf.setColumns(10);
		inputCpf.setBounds(239, 99, 197, 34);
		moradores.add(inputCpf);
		
		JLabel lblCpf = new JLabel("CPF:");
		lblCpf.setHorizontalAlignment(SwingConstants.LEFT);
		lblCpf.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		lblCpf.setBounds(239, 83, 159, 14);
		moradores.add(lblCpf);
		
		JButton botaoCadastrar = new JButton("CADASTRAR");
		botaoCadastrar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String nomeCadastro = inputNome.getText();
				String cpfCadastro = inputCpf.getText();
				try {
					Fachada.cadastrarMorador(nomeCadastro, cpfCadastro);
					JOptionPane.showMessageDialog(menuPrincipal, "Cadastro realizado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
					inputNome.setText("");
					inputCpf.setText("");
					inputNome.requestFocus();
					listagemMoradores.setText(Fachada.exibirMoradores());
				} catch (Exception erroCadastro) {
					JOptionPane.showMessageDialog(menuPrincipal, erroCadastro.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE);
					inputNome.setText("");
					inputCpf.setText("");
					inputNome.requestFocus();
				}
			}
		});
		botaoCadastrar.setBackground(new Color(0, 102, 51));
		botaoCadastrar.setForeground(new Color(255, 255, 255));
		botaoCadastrar.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		botaoCadastrar.setBounds(456, 99, 158, 34);
		moradores.add(botaoCadastrar);
		
		JSeparator separadorH2 = new JSeparator();
		separadorH2.setBounds(23, 151, 591, 7);
		moradores.add(separadorH2);
		
		JSeparator separadorH1 = new JSeparator();
		separadorH1.setBounds(227, 66, 387, 7);
		moradores.add(separadorH1);
		
		JLabel lblListar = new JLabel("LISTAR MORADORES");
		lblListar.setHorizontalAlignment(SwingConstants.CENTER);
		lblListar.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 18));
		lblListar.setBounds(78, 169, 163, 14);
		moradores.add(lblListar);
		
		JSeparator separadorV1 = new JSeparator();
		separadorV1.setOrientation(SwingConstants.VERTICAL);
		separadorV1.setBounds(318, 169, 2, 287);
		moradores.add(separadorV1);
		
		JScrollPane scrollPane = new JScrollPane();
		scrollPane.setBounds(23, 193, 272, 218);
		moradores.add(scrollPane);
		
		
		listagemMoradores.setEditable(false);
		listagemMoradores.setForeground(new Color(0, 0, 0));
		listagemMoradores.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 17));
		scrollPane.setViewportView(listagemMoradores);
		
		JButton botaoListar = new JButton("LISTAR");
		botaoListar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				listagemMoradores.setText(Fachada.exibirMoradores());
			}
		});
		botaoListar.setForeground(Color.WHITE);
		botaoListar.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		botaoListar.setBackground(new Color(0, 102, 51));
		botaoListar.setBounds(23, 422, 272, 34);
		moradores.add(botaoListar);
		
		JLabel lblFazerAcordo = new JLabel("FAZER ACORDO");
		lblFazerAcordo.setHorizontalAlignment(SwingConstants.CENTER);
		lblFazerAcordo.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 18));
		lblFazerAcordo.setBounds(392, 169, 163, 14);
		moradores.add(lblFazerAcordo);
		
		JLabel lblCpfAcordo = new JLabel("CPF:");
		lblCpfAcordo.setHorizontalAlignment(SwingConstants.LEFT);
		lblCpfAcordo.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		lblCpfAcordo.setBounds(341, 194, 159, 14);
		moradores.add(lblCpfAcordo);
		
		inputCpfAcordo = new JTextField();
		inputCpfAcordo.setToolTipText("CPF do morador.");
		inputCpfAcordo.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 15));
		inputCpfAcordo.setColumns(10);
		inputCpfAcordo.setBounds(341, 210, 159, 34);
		moradores.add(inputCpfAcordo);
		
		JTextArea exibirNome = new JTextArea();
		exibirNome.setEditable(false);
		JTextArea exibirValor = new JTextArea();
		exibirValor.setEditable(false);
		
		JButton botaoBuscar = new JButton("BUSCAR");
		botaoBuscar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cpfMorador = inputCpfAcordo.getText();
				Morador morador;
				
				if(cpfMorador.equals("")){
					JOptionPane.showMessageDialog(menuPrincipal, "O campo CPF está vazio.", "Erro", JOptionPane.WARNING_MESSAGE);
				} else {
					morador = Fachada.buscarMorador(cpfMorador);
					if(morador == null) {
						JOptionPane.showMessageDialog(menuPrincipal, "O morador de CPF "+cpfMorador+" não está cadastrado.", "Erro", JOptionPane.WARNING_MESSAGE);
						inputCpfAcordo.setText("");
						exibirNome.setText("");
						exibirValor.setText("");
						inputCpfAcordo.requestFocus();
					} else {
						try {
							morador = Fachada.buscarMorador(cpfMorador);
							exibirNome.setText(morador.getNome());
							exibirValor.setText(Double.toString(Fachada.valorAtrasados(cpfMorador)));
						} catch (Exception erroMorador) {
							JOptionPane.showMessageDialog(menuPrincipal, erroMorador.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE);
							inputCpfAcordo.setText("");
							exibirNome.setText("");
							exibirValor.setText("");
							inputCpfAcordo.requestFocus();
						}
					}
				}
			}
		});
		botaoBuscar.setForeground(Color.WHITE);
		botaoBuscar.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		botaoBuscar.setBackground(new Color(0, 102, 51));
		botaoBuscar.setBounds(510, 210, 104, 34);
		moradores.add(botaoBuscar);
		
		JLabel lblNomeAcordo = new JLabel("MORADOR:");
		lblNomeAcordo.setHorizontalAlignment(SwingConstants.LEFT);
		lblNomeAcordo.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		lblNomeAcordo.setBounds(341, 273, 273, 14);
		moradores.add(lblNomeAcordo);
		
		JButton botaoConfirmar = new JButton("CONFIRMAR");
		botaoConfirmar.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				String cpfMorador = inputCpfAcordo.getText();
				try {
					boolean acordo = Fachada.fazerAcordo(cpfMorador);
					if(acordo) {
						JOptionPane.showMessageDialog(menuPrincipal, "Acordo realizado com sucesso.", "Sucesso", JOptionPane.INFORMATION_MESSAGE);
						inputCpfAcordo.setText("");
						exibirNome.setText("");
						exibirValor.setText("");
						inputCpfAcordo.requestFocus();
					}
				} catch (Exception erroAcordo) {
					JOptionPane.showMessageDialog(menuPrincipal, erroAcordo.getMessage(), "Erro", JOptionPane.WARNING_MESSAGE);
					inputCpfAcordo.setText("");
					exibirNome.setText("");
					exibirValor.setText("");
					inputCpfAcordo.requestFocus();
				}
			}
		});
		botaoConfirmar.setForeground(Color.WHITE);
		botaoConfirmar.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		botaoConfirmar.setBackground(new Color(0, 102, 51));
		botaoConfirmar.setBounds(341, 422, 273, 34);
		moradores.add(botaoConfirmar);
		
		JLabel lblConfimar = new JLabel("Confirmar pagamento do acordo?");
		lblConfimar.setHorizontalAlignment(SwingConstants.CENTER);
		lblConfimar.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		lblConfimar.setBounds(341, 393, 273, 23);
		moradores.add(lblConfimar);
		exibirNome.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 15));
		exibirNome.setBounds(341, 291, 273, 23);
		moradores.add(exibirNome);
		
		JLabel lblValorTotal = new JLabel("VALOR TOTAL DA DÍVIDA:");
		lblValorTotal.setHorizontalAlignment(SwingConstants.LEFT);
		lblValorTotal.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		lblValorTotal.setBounds(341, 325, 273, 14);
		moradores.add(lblValorTotal);
		exibirValor.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 15));
		exibirValor.setBounds(341, 343, 273, 23);
		moradores.add(exibirValor);
		moradores.setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{lblMoradores, lblCadastrar, inputNome, lblNome, inputCpf, lblCpf, botaoCadastrar, separadorH2, separadorH1, lblListar, separadorV1, listagemMoradores, botaoListar, scrollPane, lblFazerAcordo, lblCpfAcordo, inputCpfAcordo, botaoBuscar, lblNomeAcordo, botaoConfirmar, lblConfimar, exibirNome, lblValorTotal, exibirValor}));
		
		JPanel condominios = new JPanel();
		condominios.setLayout(null);
		condominios.setBackground(new Color(225, 225, 225));
		condominios.setBounds(0, 0, 638, 480);
		menuPrincipal.getContentPane().add(condominios);
		condominios.setVisible(false);
		
		JLabel lblCondominio = new JLabel("CONDOMÍNIOS");
		lblCondominio.setVerticalAlignment(SwingConstants.TOP);
		lblCondominio.setHorizontalAlignment(SwingConstants.CENTER);
		lblCondominio.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 25));
		lblCondominio.setBounds(23, 7, 591, 25);
		condominios.add(lblCondominio);
		
		JLabel lblCadCond = new JLabel("CADASTRAR CONDOMÍNIO");
		lblCadCond.setHorizontalAlignment(SwingConstants.LEFT);
		lblCadCond.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 18));
		lblCadCond.setBounds(23, 77, 206, 30);
		condominios.add(lblCadCond);
		
		inputNomeCond = new JTextField();
		inputNomeCond.setToolTipText("Nome do morador.");
		inputNomeCond.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 15));
		inputNomeCond.setColumns(10);
		inputNomeCond.setBounds(23, 126, 206, 25);
		condominios.add(inputNomeCond);
		
		JLabel lblNomeCond = new JLabel("NOME:");
		lblNomeCond.setHorizontalAlignment(SwingConstants.LEFT);
		lblNomeCond.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		lblNomeCond.setBounds(23, 109, 159, 14);
		condominios.add(lblNomeCond);
		
		inputEndCond = new JTextField();
		inputEndCond.setToolTipText("CPF do morador.");
		inputEndCond.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 15));
		inputEndCond.setColumns(10);
		inputEndCond.setBounds(23, 181, 206, 25);
		condominios.add(inputEndCond);
		
		JLabel lblEndCond = new JLabel("ENDEREÇO:");
		lblEndCond.setHorizontalAlignment(SwingConstants.LEFT);
		lblEndCond.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		lblEndCond.setBounds(23, 162, 159, 17);
		condominios.add(lblEndCond);
		
		JButton botaoCadCond = new JButton("CADASTRAR");
		botaoCadCond.setForeground(Color.WHITE);
		botaoCadCond.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		botaoCadCond.setBackground(new Color(0, 102, 51));
		botaoCadCond.setBounds(23, 217, 206, 25);
		condominios.add(botaoCadCond);
		
		JLabel lblListarConds = new JLabel("TODOS OS CONDOMÍNIOS");
		lblListarConds.setHorizontalAlignment(SwingConstants.CENTER);
		lblListarConds.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 18));
		lblListarConds.setBounds(280, 44, 334, 23);
		condominios.add(lblListarConds);
		
		JLabel lblExcCond = new JLabel("EXCLUIR CONDOMÍNIO");
		lblExcCond.setVerticalAlignment(SwingConstants.TOP);
		lblExcCond.setHorizontalAlignment(SwingConstants.LEFT);
		lblExcCond.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 18));
		lblExcCond.setBounds(38, 295, 176, 19);
		condominios.add(lblExcCond);
		
		JLabel lblIdCond = new JLabel("ID DO CONDOMÍNIO:");
		lblIdCond.setVerticalAlignment(SwingConstants.TOP);
		lblIdCond.setHorizontalAlignment(SwingConstants.LEFT);
		lblIdCond.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		lblIdCond.setBounds(23, 327, 130, 18);
		condominios.add(lblIdCond);
		
		inputIdCond = new JTextField();
		inputIdCond.setToolTipText("Nome do morador.");
		inputIdCond.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 15));
		inputIdCond.setColumns(10);
		inputIdCond.setBounds(23, 348, 206, 25);
		condominios.add(inputIdCond);
		
		JButton botaoExcCond = new JButton("EXCLUIR");
		botaoExcCond.setForeground(Color.WHITE);
		botaoExcCond.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		botaoExcCond.setBackground(new Color(0, 102, 51));
		botaoExcCond.setBounds(23, 384, 206, 25);
		condominios.add(botaoExcCond);
		
		JSeparator separadorHCond = new JSeparator();
		separadorHCond.setOrientation(SwingConstants.VERTICAL);
		separadorHCond.setBounds(253, 64, 17, 394);
		condominios.add(separadorHCond);
		
		JLabel lblOcupConds = new JLabel("OCUPAÇÃO DOS CONDOMÍNIOS");
		lblOcupConds.setHorizontalAlignment(SwingConstants.CENTER);
		lblOcupConds.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 18));
		lblOcupConds.setBounds(280, 189, 334, 23);
		condominios.add(lblOcupConds);
		
		JLabel lblInads = new JLabel("INADIMPLENTES");
		lblInads.setHorizontalAlignment(SwingConstants.CENTER);
		lblInads.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 18));
		lblInads.setBounds(280, 334, 334, 23);
		condominios.add(lblInads);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		scrollPane_1.setBounds(280, 71, 334, 80);
		condominios.add(scrollPane_1);
		
		JTextArea exibirTodosConds = new JTextArea();
		scrollPane_1.setViewportView(exibirTodosConds);
		exibirTodosConds.setEnabled(false);
		exibirTodosConds.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 15));
		
		JButton botaoListarConds = new JButton("LISTAR");
		botaoListarConds.setForeground(Color.WHITE);
		botaoListarConds.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		botaoListarConds.setBackground(new Color(0, 102, 51));
		botaoListarConds.setBounds(280, 152, 334, 25);
		condominios.add(botaoListarConds);
		
		JButton botaoListarOcup = new JButton("LISTAR");
		botaoListarOcup.setForeground(Color.WHITE);
		botaoListarOcup.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		botaoListarOcup.setBackground(new Color(0, 102, 51));
		botaoListarOcup.setBounds(280, 297, 334, 25);
		condominios.add(botaoListarOcup);
		
		JScrollPane scrollPane_2 = new JScrollPane();
		scrollPane_2.setBounds(280, 216, 334, 80);
		condominios.add(scrollPane_2);
		
		JTextArea exibirOcupConds = new JTextArea();
		scrollPane_2.setViewportView(exibirOcupConds);
		exibirOcupConds.setEnabled(false);
		exibirOcupConds.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 15));
		
		JScrollPane scrollPane_3 = new JScrollPane();
		scrollPane_3.setBounds(280, 358, 334, 80);
		condominios.add(scrollPane_3);
		
		JTextArea exibirInads = new JTextArea();
		scrollPane_3.setViewportView(exibirInads);
		exibirInads.setEnabled(false);
		exibirInads.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 15));
		
		JButton botaoListarInads = new JButton("LISTAR");
		botaoListarInads.setForeground(Color.WHITE);
		botaoListarInads.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		botaoListarInads.setBackground(new Color(0, 102, 51));
		botaoListarInads.setBounds(280, 439, 334, 25);
		condominios.add(botaoListarInads);
		
		JPanel contratos = new JPanel();
		contratos.setLayout(null);
		contratos.setBackground(new Color(225, 225, 225));
		contratos.setBounds(0, 0, 638, 480);
		menuPrincipal.getContentPane().add(contratos);
		contratos.setVisible(false);
		
		JLabel lblContratos = new JLabel("CONTRATOS");
		lblContratos.setHorizontalAlignment(SwingConstants.CENTER);
		lblContratos.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 25));
		lblContratos.setBounds(23, 11, 592, 23);
		contratos.add(lblContratos);
		
		JLabel lblCriarCont = new JLabel("CRIAR CONTRATO");
		lblCriarCont.setHorizontalAlignment(SwingConstants.CENTER);
		lblCriarCont.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 18));
		lblCriarCont.setBounds(23, 60, 273, 14);
		contratos.add(lblCriarCont);
		
		JSeparator separadorVCont = new JSeparator();
		separadorVCont.setOrientation(SwingConstants.VERTICAL);
		separadorVCont.setBounds(318, 71, 15, 271);
		contratos.add(separadorVCont);
		
		JLabel lblEncCont = new JLabel("ENCERRAR CONTRATO");
		lblEncCont.setHorizontalAlignment(SwingConstants.CENTER);
		lblEncCont.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 18));
		lblEncCont.setBounds(343, 60, 272, 14);
		contratos.add(lblEncCont);
		
		inputIdCC = new JTextField();
		inputIdCC.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 15));
		inputIdCC.setBounds(24, 112, 272, 32);
		contratos.add(inputIdCC);
		inputIdCC.setColumns(10);
		
		JLabel lblIdCC = new JLabel("ID DO CONDOMÍNIO:");
		lblIdCC.setVerticalAlignment(SwingConstants.TOP);
		lblIdCC.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		lblIdCC.setBounds(24, 91, 272, 17);
		contratos.add(lblIdCC);
		
		JLabel lblCpfCC = new JLabel("CPF DO CLIENTE:");
		lblCpfCC.setVerticalAlignment(SwingConstants.TOP);
		lblCpfCC.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		lblCpfCC.setBounds(24, 155, 272, 17);
		contratos.add(lblCpfCC);
		
		inputCpfCC = new JTextField();
		inputCpfCC.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 15));
		inputCpfCC.setColumns(10);
		inputCpfCC.setBounds(24, 176, 272, 32);
		contratos.add(inputCpfCC);
		
		JLabel lblValorAluguel = new JLabel("VALOR DO ALUGUEL:");
		lblValorAluguel.setVerticalAlignment(SwingConstants.TOP);
		lblValorAluguel.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		lblValorAluguel.setBounds(24, 219, 272, 17);
		contratos.add(lblValorAluguel);
		
		inputValorAluguel = new JTextField();
		inputValorAluguel.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 15));
		inputValorAluguel.setColumns(10);
		inputValorAluguel.setBounds(24, 240, 272, 32);
		contratos.add(inputValorAluguel);
		
		JButton botaoCriarCont = new JButton("CRIAR CONTRATO");
		botaoCriarCont.setForeground(new Color(255, 255, 255));
		botaoCriarCont.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		botaoCriarCont.setBackground(new Color(0, 102, 51));
		botaoCriarCont.setBounds(24, 294, 272, 32);
		contratos.add(botaoCriarCont);
		
		JLabel lblIdEC = new JLabel("ID DO CONDOMÍNIO:");
		lblIdEC.setVerticalAlignment(SwingConstants.TOP);
		lblIdEC.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		lblIdEC.setBounds(343, 122, 272, 17);
		contratos.add(lblIdEC);
		
		inputIdEC = new JTextField();
		inputIdEC.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 15));
		inputIdEC.setColumns(10);
		inputIdEC.setBounds(343, 143, 272, 32);
		contratos.add(inputIdEC);
		
		JLabel lblCpfEC = new JLabel("CPF DO CLIENTE:");
		lblCpfEC.setVerticalAlignment(SwingConstants.TOP);
		lblCpfEC.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		lblCpfEC.setBounds(343, 186, 272, 17);
		contratos.add(lblCpfEC);
		
		inputCpfEC = new JTextField();
		inputCpfEC.setFont(new Font("Core Sans DS 35 Regular", Font.PLAIN, 15));
		inputCpfEC.setColumns(10);
		inputCpfEC.setBounds(343, 207, 272, 32);
		contratos.add(inputCpfEC);
		
		JButton botaoEncCont = new JButton("ENCERRAR CONTRATO");
		botaoEncCont.setForeground(Color.WHITE);
		botaoEncCont.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		botaoEncCont.setBackground(new Color(0, 102, 51));
		botaoEncCont.setBounds(343, 261, 272, 32);
		contratos.add(botaoEncCont);
		
		JSeparator separadorHCont = new JSeparator();
		separadorHCont.setBounds(23, 365, 593, 8);
		contratos.add(separadorHCont);
		
		JLabel lblBoletos = new JLabel("GERAÇÃO DE BOLETOS");
		lblBoletos.setVerticalAlignment(SwingConstants.TOP);
		lblBoletos.setHorizontalAlignment(SwingConstants.CENTER);
		lblBoletos.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 20));
		lblBoletos.setBounds(23, 367, 592, 25);
		contratos.add(lblBoletos);
		
		JButton botaoGerarBol = new JButton("GERAR BOLETOS");
		botaoGerarBol.setForeground(Color.WHITE);
		botaoGerarBol.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		botaoGerarBol.setBackground(new Color(0, 102, 51));
		botaoGerarBol.setBounds(23, 437, 273, 32);
		contratos.add(botaoGerarBol);
		
		lblMesRef.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		lblMesRef.setHorizontalAlignment(SwingConstants.CENTER);
		lblMesRef.setBounds(23, 408, 273, 23);
		contratos.add(lblMesRef);
		
		JTextArea fundoMesRef = new JTextArea();
		fundoMesRef.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		fundoMesRef.setBounds(23, 408, 273, 23);
		contratos.add(fundoMesRef);
		
		JLabel tituloMesRef = new JLabel("MÊS DE REFERÊNCIA:");
		tituloMesRef.setVerticalAlignment(SwingConstants.TOP);
		tituloMesRef.setHorizontalAlignment(SwingConstants.LEFT);
		tituloMesRef.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		tituloMesRef.setBounds(23, 388, 273, 17);
		contratos.add(tituloMesRef);
		
		JLabel tituloQntBol = new JLabel("BOLETOS GERADOS:");
		tituloQntBol.setHorizontalAlignment(SwingConstants.CENTER);
		tituloQntBol.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		tituloQntBol.setBounds(441, 389, 174, 14);
		contratos.add(tituloQntBol);
		
		JLabel lblQntBol = new JLabel("");
		lblQntBol.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 30));
		lblQntBol.setHorizontalAlignment(SwingConstants.CENTER);
		lblQntBol.setBounds(441, 408, 174, 61);
		contratos.add(lblQntBol);
		
		JTextArea fundoQntBol = new JTextArea();
		fundoQntBol.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 25));
		fundoQntBol.setEnabled(false);
		fundoQntBol.setBounds(441, 408, 174, 61);
		contratos.add(fundoQntBol);
		
		JMenuBar menuBar = new JMenuBar();
		menuPrincipal.setJMenuBar(menuBar);
		
		JButton botaoMoradores = new JButton("            MORADORES            ");
		JButton botaoCondominios = new JButton("           CONDOMÍNIOS           ");
		JButton botaoContratos = new JButton("            CONTRATOS            ");
		
		botaoMoradores.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				condominios.setVisible(false);
				contratos.setVisible(false);
				moradores.setVisible(true);
				botaoMoradores.setForeground(new Color(0, 102, 153));
				botaoMoradores.setBackground(new Color(225, 225, 225));
				
				botaoCondominios.setBackground(new Color(0, 102, 153));
				botaoCondominios.setForeground(new Color(225, 225, 225));
				
				botaoContratos.setBackground(new Color(0, 102, 153));
				botaoContratos.setForeground(new Color(225, 225, 225));
			}
		});
		botaoMoradores.setFocusable(false);
		botaoMoradores.setBackground(new Color(225, 225, 225));
		botaoMoradores.setForeground(new Color(0, 102, 153));
		botaoMoradores.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		botaoMoradores.setEnabled(true);
		menuBar.add(botaoMoradores);
		
		botaoCondominios.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moradores.setVisible(false);
				contratos.setVisible(false);
				condominios.setVisible(true);
				botaoCondominios.setForeground(new Color(0, 102, 153));
				botaoCondominios.setBackground(new Color(225, 225, 225));
				
				botaoContratos.setBackground(new Color(0, 102, 153));
				botaoContratos.setForeground(new Color(225, 225, 225));
				
				botaoMoradores.setBackground(new Color(0, 102, 153));
				botaoMoradores.setForeground(new Color(225, 225, 225));
			}
		});
		botaoCondominios.setFocusable(false);
		botaoCondominios.setBackground(new Color(0, 102, 153));
		botaoCondominios.setForeground(new Color(255, 255, 255));
		botaoCondominios.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		menuBar.add(botaoCondominios);
		
		botaoContratos.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				moradores.setVisible(false);
				condominios.setVisible(false);
				contratos.setVisible(true);
				botaoContratos.setForeground(new Color(0, 102, 153));
				botaoContratos.setBackground(new Color(225, 225, 225));
				
				botaoCondominios.setBackground(new Color(0, 102, 153));
				botaoCondominios.setForeground(new Color(225, 225, 225));
				
				botaoMoradores.setBackground(new Color(0, 102, 153));
				botaoMoradores.setForeground(new Color(225, 225, 225));
			}
		});
		botaoContratos.setFocusable(false);
		botaoContratos.setBackground(new Color(0, 102, 153));
		botaoContratos.setForeground(new Color(255, 255, 255));
		botaoContratos.setFont(new Font("Core Sans DS 55 Bold", Font.PLAIN, 15));
		menuBar.add(botaoContratos);
	}
}
