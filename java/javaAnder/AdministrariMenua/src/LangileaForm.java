import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LangileaForm extends JDialog {

	// TextField-ak hasieratu
	private JTextField txtPosta, txtPasahitza, txtIzena, txtAbizena, txtNAN, txtMatrikula, txtEdukiera, txtEgoera,
			txtTarifa;

	// Botoia hasieratu
	private JButton btnGorde;

	public LangileaForm(JFrame parent) {

		// Titulua eta layout mota ezarri
		super(parent, "Gidari berria gehitu", true);
		setLayout(new GridLayout(10, 2));

		// Erabiltzailearen datuak
		add(new JLabel("Posta:"));
		txtPosta = new JTextField();
		add(txtPosta);

		add(new JLabel("Pasahitza:"));
		txtPasahitza = new JTextField();
		add(txtPasahitza);

		// Langilearen datuak
		add(new JLabel("Izena:"));
		txtIzena = new JTextField();
		add(txtIzena);

		add(new JLabel("Abizena:"));
		txtAbizena = new JTextField();
		add(txtAbizena);

		add(new JLabel("NAN:"));
		txtNAN = new JTextField();
		add(txtNAN);

		// Taxistaren datuak
		add(new JLabel("Matrikula:"));
		txtMatrikula = new JTextField();
		add(txtMatrikula);

		add(new JLabel("Edukiera:"));
		txtEdukiera = new JTextField();
		add(txtEdukiera);

		add(new JLabel("Egoera:"));
		txtEgoera = new JTextField();
		add(txtEgoera);

		add(new JLabel("Tarifa:"));
		txtTarifa = new JTextField();
		add(txtTarifa);

		// Botoia
		btnGorde = new JButton("Gorde");
		add(btnGorde);

		// Botoiaren listenerra
		btnGorde.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {

				// sortuLangilea funtzioa deitu sakatzean
				sortuLangilea();
			}
		});

		// Tamaina ezarri
		setSize(400, 300);
		setLocationRelativeTo(parent);
	}

	private void sortuLangilea() {

		try {

			// TextField-etatik jasotako baloreak aldagaietan gorde, parseatuz beharrezkoa baldin bada
			String posta = txtPosta.getText().trim();
			String pasahitza = txtPasahitza.getText().trim();
			String izena = txtIzena.getText().trim();
			String abizena = txtAbizena.getText().trim();
			String nan = txtNAN.getText().trim();
			String matrikula = txtMatrikula.getText().trim();
			int edukiera = Integer.parseInt(txtEdukiera.getText().trim());
			String egoera = txtEgoera.getText().trim();
			double tarifa = Double.parseDouble(txtTarifa.getText().trim());

			// Langilea sortzeko metodoari deitu
			boolean ondo = Metodoak.gidariGehitu(posta, pasahitza, izena, abizena, nan, matrikula, edukiera, egoera,
					tarifa);
			
			if (ondo) {
				
				// Dena ondo baldin badijo mezua erakutsi eta itxi
				JOptionPane.showMessageDialog(this, "Taxista ondo sortu da.");
				dispose();
				
			} else {
				
				// Bestela arazo bat egon dela esan
				JOptionPane.showMessageDialog(this, "Arazo bat egon da.");
				
			}
		} catch (Exception e) {

			System.out.print(e.getMessage());
			JOptionPane.showMessageDialog(this, "Arazo bat egon da.");
			
		}
	}
}
