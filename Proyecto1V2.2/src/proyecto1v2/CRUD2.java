package proyecto1v2;

import Excepciones.CamposVacios;
import javax.swing.JOptionPane;
import Excepciones.*;
import java.util.*;
import java.util.Calendar;
import ManejoDeArchivos.*;
import java.text.SimpleDateFormat;
import java.text.DateFormat;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
/**
 *
 * @author invitado
 */
public class CRUD2 extends javax.swing.JFrame {

    private ArrayList<Bus> buses = new ArrayList<Bus>(); //Array que contiene todos los buses
    private ArrayList<Ruta> rutas = new ArrayList<Ruta>(); //Array que contiene todas las rutas
    private ArrayList<Tarjeta> tarjetas = new ArrayList<Tarjeta>(); //Array que contiene todas las tarjetas
    private EscribirArchivo file = new EscribirArchivo(); //Objeto para guardar en un archivo de texto
    private LeerArchivo archivo = new LeerArchivo(); //Objeto para leer de un archivo de texto
    private ArrayList<String> fechasRecargas = new ArrayList<String>(); //Array que contiene la informacion de las fechas de recargas (fecha, valor recargado)
    private ArrayList<String> fechasDescargas = new ArrayList<String>(); //Array que contiene la informacion de las fechas de uso de una tarjeta (codigo tarjeta, fecha de uso)

    private Calendar FechaActual;

    public CRUD2() {
        initComponents();
        FechaActual = Calendar.getInstance();
        //Carga de archivos
        archivo.readFile("rutas.txt", 1);
        archivo.readFile("buses.txt", 2);
        archivo.readFile("tarjetas.txt", 3);
        archivo.readFile("fechasRecargas.txt", 4);
        archivo.readFile("fechasExtracciones.txt", 5);
        //Agregacion de lo que se cargó de archivos a los vectores
        this.agregarRutas();
        this.agregarTarjetas();
        this.agregarBuses();
        this.agregarFechasRecargas();
        this.agregarFechasDescargas();
    }

    public ArrayList<Bus> getBuses() {
        return buses;
    }

    public void setBuses(ArrayList<Bus> buses) {
        this.buses = buses;
    }

    public ArrayList<Ruta> getRutas() {
        return rutas;
    }

    public void setRutas(ArrayList<Ruta> rutas) {
        this.rutas = rutas;
    }

    public ArrayList<Tarjeta> getTarjetas() {
        return tarjetas;
    }

    public void setTarjetas(ArrayList<Tarjeta> tarjetas) {
        this.tarjetas = tarjetas;
    }

    /**
     * Creates new form CRUD
     */
    //METODOS 
    public boolean existeTar(int numTar) { //Metodo para determinar si una tarjeta ya existe
        boolean resultado = false;

        for (int i = 0; i < tarjetas.size(); i++) {
            Tarjeta tI = (Tarjeta) tarjetas.get(i);

            if (tI.getNumero() == numTar) {
                resultado = true;
            }
        }
        return resultado;
    }

    public boolean existe(Bus bus) {//Metodo para determinar si un bus ya existe
        boolean resultado = false;
        for (int i = 0; i < buses.size(); i++) {
            if (buses.get(i).getPlaca().equals(bus.getPlaca())) {
                resultado = true;
            }
        }
        return resultado;
    }

    public boolean existe(Ruta ruta) {//Metodo para determinar si un bus ya existe
        boolean resultado = false;
        for (int i = 0; i < rutas.size(); i++) {
            if (rutas.get(i).getCodigo() == ruta.getCodigo()) {
                resultado = true;
            }
        }
        return resultado;
    }

    public boolean soloLetras(final CharSequence input) {//Metodo que usa la clase Cadena para la excepcion de sólo letras en un campo de texto
        String str = "";
        for (int i = 0; i < input.length(); i++) {
            final char c = input.charAt(i);
            if ((c == 32) || (c > 64 && c < 91) || (c > 96 && c < 123)) {
                str += c;
            }
        }
        if (str.equals(input)) {
            return false;
        } else {
            return true;
        }
    }

    public void agregarRutas() {//Agregación de las rutas del archivo al Array de rutas
        int codigo = 0;
        String nombre = "";
        String descripcion = "";
        String tipo = "";
        Ruta rut;
        for (int i = 0; i < archivo.getRuta().size(); i++) {
            if (i == 0 || (i % 4) == 0) { //Tomando las posiciones como multiplos de 2
                codigo = Integer.parseInt((String) archivo.getRuta().get(i));
            } else if ((i - 1) == 0 || ((i - 1) % 4) == 0) {
                nombre = (String) archivo.getRuta().get(i);
            } else if ((i - 2) == 0 || ((i - 2) % 4) == 0) {
                descripcion = (String) archivo.getRuta().get(i);
            } else if ((i - 3) == 0 || ((i - 3) % 4) == 0) {
                tipo = (String) archivo.getRuta().get(i);
                rut = new Ruta(codigo, nombre, tipo, descripcion);
                rutas.add(rut);
                jComboBoxCodRuta.addItem(String.valueOf(rut.getCodigo()));
                jComboBoxRutaBus.addItem(rut.getNombre() + "-" + rut.getCodigo() + " : " + rut.getOrigenDestino());

            }
        }
    }

    public void agregarBuses() { //Método para agregar buses del archivo al Array
        String placa = "";
        int modelo = 0;
        String marca = "";
        String tipo = "";
        int capacidad = 0;
        int codRuta = 0;
        Ruta rut = new Ruta(0, "", "", "");
        Bus bus;
        for (int i = 0; i < archivo.getBus().size(); i++) {
            if (i == 0 || (i % 6) == 0) {//Tomando las posiciones como multiplos de 6
                placa = (String) archivo.getBus().get(i);
            } else if (i - 1 == 0 || ((i - 1) % 6) == 0) {
                String model = (String) archivo.getBus().get(i);
                modelo = Integer.parseInt(model.replace(" ", ""));
            } else if (i - 2 == 0 || ((i - 2) % 6) == 0) {
                marca = (String) archivo.getBus().get(i);
            } else if (i - 3 == 0 || ((i - 3) % 6) == 0) {
                tipo = (String) archivo.getBus().get(i);
            } else if (i - 4 == 0 || ((i - 4) % 6) == 0) {
                String cap = (String) archivo.getBus().get(i);
                capacidad = Integer.parseInt(cap.replace(" ", ""));
            } else if (i - 5 == 0 || ((i - 5) % 6) == 0) {
                String cod = (String) archivo.getBus().get(i);
                codRuta = Integer.parseInt(cod.replace(" ", ""));
                for (int j = 0; j < rutas.size(); j++) {
                    if (rutas.get(j).getCodigo() == codRuta) {
                        rut.setCodigo(rutas.get(j).getCodigo());
                        rut.setNombre(rutas.get(j).getNombre());
                        rut.setTipo(rutas.get(j).getTipo());
                        rut.setOrigenDestino(rutas.get(j).getOrigenDestino());
                    }
                }
                bus = new Bus(placa, modelo, marca, tipo, capacidad, rut);
                buses.add(bus);
                jComboBoxPlacaBus.addItem(String.valueOf(bus.getPlaca()));
                jComboBoxBuses.addItem(String.valueOf(bus.getPlaca()));
            }
        }

    }

    public void agregarTarjetas() {
        int numTar = 0;
        int saldo = 0;
        int idPro = 0;
        String nombrePro = "";
        String direccPro = "";
        String fechaI = "";

        Tarjeta tarjetaII;

        for (int i = 0; i < archivo.getTarjeta().size(); i++) {
            if (i == 0 || (i % 6) == 0) {
                String numeroT = (String) archivo.getTarjeta().get(i);
                numTar = Integer.parseInt(numeroT.replace(" ", ""));
            } else if ((i - 1) == 0 || ((i - 1) % 6) == 0) {
                String saldoT = (String) archivo.getTarjeta().get(i);
                saldo = Integer.parseInt(saldoT.replace(" ", ""));
            } else if ((i - 2) == 0 || ((i - 2) % 6) == 0) {
                nombrePro = (String) archivo.getTarjeta().get(i);

            } else if ((i - 3) == 0 || ((i - 3) % 6) == 0) {
                direccPro = (String) archivo.getTarjeta().get(i);

            } else if ((i - 4) == 0 || ((i - 4) % 6) == 0) {
                String idProp = (String) archivo.getTarjeta().get(i);
                idPro = Integer.parseInt(idProp.replace(" ", ""));

            } else if ((i - 5) == 0 || ((i - 5) % 6) == 0) {

                fechaI = (String) archivo.getTarjeta().get(i);

                tarjetaII = new Tarjeta();

                tarjetaII.setNumero(numTar);
                tarjetaII.setSaldo(saldo);
                tarjetaII.setIdUsuario(idPro);
                tarjetaII.setNombreUsuario(nombrePro);
                tarjetaII.setDireccionUsuario(direccPro);
                tarjetaII.setFechaCompra(fechaI);
                tarjetas.add(tarjetaII);
                jComboBoxNumTarI.addItem(String.valueOf(tarjetaII.getNumero()));
                jComboBoxNumTarII.addItem(String.valueOf(tarjetaII.getNumero()));
                jComboBoxNumTarjeIII.addItem(String.valueOf(tarjetaII.getNumero()));

            }

        }
    }

    public void agregarFechasRecargas() {//Agrega las fechas de recarga del archivo al Array de recargas  (fecha, valor)
        Tarjeta tarjetaIII;
        for (int i = 0; i < archivo.getFechasRecargas().size(); i++) {
            if (i == 0 || i % 2 == 0) {//Tomando las posiciones como pares
                fechasRecargas.add(archivo.getFechasRecargas().get(i));
            } else if (i - 1 == 0 || ((i - 1) % 2) == 0) {
                fechasRecargas.add(archivo.getFechasRecargas().get(i));
            }
        }
    }

    public void agregarFechasDescargas() {//Agrega las fechas de descarga del archivo al Array de descargas (codigo, fecha)
        int codigo = 0;
        for (int i = 0; i < archivo.getFechasDescargas().size(); i++) {
            if (i == 0 || (i % 2) == 0) {//Tomando sólo las posiciones pares
                codigo = Integer.parseInt(archivo.getFechasDescargas().get(i));
                for (int j = 0; j < tarjetas.size(); j++) {
                    if (codigo == tarjetas.get(j).getNumero()) {
                        int saldo = tarjetas.get(j).getSaldo() - 2000;
                        tarjetas.get(j).setSaldo(saldo);
                    }
                }
            }
        }
        for (int i = 0; i < archivo.getFechasDescargas().size(); i++) {//Añadiendo la informacion al Array de descargas de la clase CRUD
            fechasDescargas.add(archivo.getFechasDescargas().get(i));
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

        buttonGroup1 = new javax.swing.ButtonGroup();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel4 = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        jTabbedPaneCrear = new javax.swing.JTabbedPane();
        jPanel7 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jComboBoxTiposRuta = new javax.swing.JComboBox<>();
        jLabelCodigoRuta = new javax.swing.JLabel();
        jTextFieldCodigoRuta = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabelOrigenDestino = new javax.swing.JLabel();
        jTextFieldOrigen = new javax.swing.JTextField();
        jLabelInformacion1 = new javax.swing.JLabel();
        jLabelInformacion2 = new javax.swing.JLabel();
        jTextFieldDestino = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jButtonGuardarRuta = new javax.swing.JButton();
        jLabelNombre = new javax.swing.JLabel();
        jTextFieldNombre = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabelPlaca = new javax.swing.JLabel();
        jLabelModelo = new javax.swing.JLabel();
        jLabelMarca = new javax.swing.JLabel();
        jTextFieldPlacaLet = new javax.swing.JTextField();
        jTextFieldMarca = new javax.swing.JTextField();
        jLabelTipo = new javax.swing.JLabel();
        jComboBoxTipo = new javax.swing.JComboBox<>();
        jLabelCapacidadPasajeros = new javax.swing.JLabel();
        jSpinnerCantidadPasajeros = new javax.swing.JSpinner();
        jButtonGuardarBus = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        jComboBoxRutaBus = new javax.swing.JComboBox<>();
        jSpinnerAnioModelo = new javax.swing.JSpinner();
        jTextFieldPlacaNum = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jPanel10 = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        TFnombreProp = new javax.swing.JTextField();
        TFidProp = new javax.swing.JTextField();
        jComboBoxDireccion1 = new javax.swing.JComboBox<>();
        jTextFieldDireccion1 = new javax.swing.JTextField();
        jComboBoxDireccion2 = new javax.swing.JComboBox<>();
        jTextFieldDireccion2 = new javax.swing.JTextField();
        jLabel24 = new javax.swing.JLabel();
        jTextFieldDireccion3 = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jTextFieldDireccion4 = new javax.swing.JTextField();
        jLabel26 = new javax.swing.JLabel();
        jLabel27 = new javax.swing.JLabel();
        jPanel13 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        TFNumeroTarjetaI = new javax.swing.JTextField();
        TFsaldoI = new javax.swing.JTextField();
        BGuardarTarjeta = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        jPanelListarTarjetas = new javax.swing.JPanel();
        jButtonListarTarjetas = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextAreaListarTarjetas = new javax.swing.JTextArea();
        jPanelListarBuses = new javax.swing.JPanel();
        jButtonListarBuses = new javax.swing.JButton();
        jScrollPane2 = new javax.swing.JScrollPane();
        jTextAreaListarBuses = new javax.swing.JTextArea();
        jPanelListarRutas = new javax.swing.JPanel();
        jButtonListarRutas = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        jTextAreaListarRutas = new javax.swing.JTextArea();
        jPanel14 = new javax.swing.JPanel();
        jPanelConsultarRuta1 = new javax.swing.JPanel();
        jLabelConsultarPorCodigo1 = new javax.swing.JLabel();
        jButtonConsultarRuta = new javax.swing.JButton();
        jComboBoxCodRuta = new javax.swing.JComboBox<>();
        jPanel15 = new javax.swing.JPanel();
        jLabel9 = new javax.swing.JLabel();
        jButton4 = new javax.swing.JButton();
        jComboBoxNumTarjeIII = new javax.swing.JComboBox<>();
        jPanel16 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jButton3 = new javax.swing.JButton();
        jComboBoxPlacaBus = new javax.swing.JComboBox<>();
        jPanel19 = new javax.swing.JPanel();
        jButtonConsultarPasajeros = new javax.swing.JButton();
        jLabel15 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jTextFieldAnio = new javax.swing.JTextField();
        jComboBoxInformacionPasajeros = new javax.swing.JComboBox<>();
        jPanel20 = new javax.swing.JPanel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jButtonRecargas = new javax.swing.JButton();
        jComboBox1 = new javax.swing.JComboBox<>();
        jLabel22 = new javax.swing.JLabel();
        jLabel23 = new javax.swing.JLabel();
        jTextField2 = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        jPanel17 = new javax.swing.JPanel();
        jLabel12 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jTextFieldValorARecargar = new javax.swing.JTextField();
        jComboBoxNumTarI = new javax.swing.JComboBox<>();
        jButtonRecargar = new javax.swing.JButton();
        jPanel18 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jComboBoxNumTarII = new javax.swing.JComboBox<>();
        jButtonPagarPasaje = new javax.swing.JButton();
        jComboBoxBuses = new javax.swing.JComboBox<>();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jPanel1.setLayout(new javax.swing.BoxLayout(jPanel1, javax.swing.BoxLayout.PAGE_AXIS));

        jPanel4.setLayout(new java.awt.GridLayout(1, 0));
        jPanel1.add(jPanel4);

        jLabel1.setText("Tipo de ruta:");

        jComboBoxTiposRuta.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Expresa", "Troncal", "Pretroncal", "Alimentadora" }));
        jComboBoxTiposRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxTiposRutaActionPerformed(evt);
            }
        });

        jLabelCodigoRuta.setText("Codigo de la ruta: ");

        jPanel8.setBorder(javax.swing.BorderFactory.createTitledBorder("Descripción"));

        jLabelOrigenDestino.setText("Origen:");

        jLabelInformacion1.setText("Desde donde sale la ruta y donde termina");

        jLabelInformacion2.setText(" Ejemplo: Centro Administrativo - Zona industrial");

        jLabel10.setText("Destino:");

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addComponent(jLabelOrigenDestino)
                        .addGap(31, 31, 31)
                        .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addComponent(jTextFieldOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, 95, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(183, 183, 183)
                                .addComponent(jLabel10)
                                .addGap(27, 27, 27)
                                .addComponent(jTextFieldDestino, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel8Layout.createSequentialGroup()
                                .addGap(81, 81, 81)
                                .addComponent(jLabelInformacion1))))
                    .addGroup(jPanel8Layout.createSequentialGroup()
                        .addGap(170, 170, 170)
                        .addComponent(jLabelInformacion2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelOrigenDestino)
                    .addComponent(jTextFieldOrigen, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldDestino, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel10))
                .addGap(25, 25, 25)
                .addComponent(jLabelInformacion1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabelInformacion2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jButtonGuardarRuta.setText("Guardar Ruta");
        jButtonGuardarRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarRutaActionPerformed(evt);
            }
        });

        jLabelNombre.setText("Nombre: ");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 0, Short.MAX_VALUE)
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGap(0, 215, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(24, 24, 24)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel1)
                    .addComponent(jLabelCodigoRuta))
                .addGap(44, 44, 44)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jComboBoxTiposRuta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldCodigoRuta))
                .addGap(57, 57, 57)
                .addComponent(jLabelNombre)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonGuardarRuta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jTextFieldNombre))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addGap(18, 18, 18)
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jComboBoxTiposRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelNombre)
                    .addComponent(jTextFieldNombre, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(23, 23, 23)
                        .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jTextFieldCodigoRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabelCodigoRuta)))
                    .addGroup(jPanel7Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addComponent(jButtonGuardarRuta)))
                .addGap(18, 18, 18)
                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        jTabbedPaneCrear.addTab("Ruta", jPanel7);

        jLabelPlaca.setText("Placa:");

        jLabelModelo.setText("Modelo:");

        jLabelMarca.setText("Marca:");

        jTextFieldPlacaLet.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyTyped(java.awt.event.KeyEvent evt) {
                jTextFieldPlacaLetKeyTyped(evt);
            }
        });

        jLabelTipo.setText("Tipo:");

        jComboBoxTipo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Expreso", "Troncal", "Pretroncal", "Alimentador" }));

        jLabelCapacidadPasajeros.setText("Capacidad de pasajeros: ");

        jSpinnerCantidadPasajeros.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        jButtonGuardarBus.setText("Guardar bus");
        jButtonGuardarBus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonGuardarBusActionPerformed(evt);
            }
        });

        jLabel2.setText("Ruta: ");

        jComboBoxRutaBus.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxRutaBusActionPerformed(evt);
            }
        });

        jSpinnerAnioModelo.setModel(new javax.swing.SpinnerNumberModel(1900, 1900, null, 1));

        jLabel11.setText(" - ");

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabelCapacidadPasajeros)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabelModelo)
                            .addComponent(jLabelMarca)
                            .addComponent(jLabelTipo)
                            .addComponent(jLabel2)
                            .addComponent(jLabelPlaca))
                        .addGap(122, 122, 122)
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel9Layout.createSequentialGroup()
                                .addComponent(jTextFieldPlacaLet, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(4, 4, 4)
                                .addComponent(jLabel11)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jTextFieldPlacaNum, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(jTextFieldMarca)
                            .addComponent(jComboBoxTipo, 0, 1, Short.MAX_VALUE)
                            .addComponent(jSpinnerCantidadPasajeros)
                            .addComponent(jComboBoxRutaBus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jSpinnerAnioModelo, javax.swing.GroupLayout.Alignment.TRAILING)))
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGap(184, 184, 184)
                        .addComponent(jButtonGuardarBus)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelPlaca)
                    .addComponent(jTextFieldPlacaLet, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jTextFieldPlacaNum, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel11))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelModelo)
                    .addComponent(jSpinnerAnioModelo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelMarca)
                    .addComponent(jTextFieldMarca, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxTipo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabelTipo))
                .addGap(33, 33, 33)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelCapacidadPasajeros)
                    .addComponent(jSpinnerCantidadPasajeros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBoxRutaBus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel2))
                .addGap(46, 46, 46)
                .addComponent(jButtonGuardarBus)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jTabbedPaneCrear.addTab("Bus", jPanel9);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Información del Propietario"));

        jLabel6.setText("Nombre:");

        jLabel7.setText("ID:");

        jLabel8.setText("Dirección:");

        jComboBoxDireccion1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Carrera", "Calle", "Avenida", "Transversal", "Autopista", "Diagonal" }));

        jComboBoxDireccion2.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "N/A", "Carrera", "Calle", "Avenida", "Transversal", "Autopista", "Diagonal", "Sur", "Norte", "Este", "Oeste" }));

        jLabel24.setText("#");

        jLabel25.setText("-");

        jLabel26.setText("No digite nada en este");

        jLabel27.setText("campo si no aplica");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGap(40, 40, 40)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel7)
                    .addComponent(jLabel6)
                    .addComponent(jLabel8))
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(21, 21, 21)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(TFnombreProp, javax.swing.GroupLayout.DEFAULT_SIZE, 84, Short.MAX_VALUE)
                            .addComponent(TFidProp))
                        .addContainerGap())
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jLabel24)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldDireccion3))
                            .addComponent(jComboBoxDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addComponent(jTextFieldDireccion1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jComboBoxDireccion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldDireccion2, javax.swing.GroupLayout.PREFERRED_SIZE, 83, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(52, 52, 52))
                            .addGroup(jPanel12Layout.createSequentialGroup()
                                .addGap(8, 8, 8)
                                .addComponent(jLabel25)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(jTextFieldDireccion4, javax.swing.GroupLayout.PREFERRED_SIZE, 68, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 76, Short.MAX_VALUE)
                                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel26)
                                    .addGroup(jPanel12Layout.createSequentialGroup()
                                        .addGap(10, 10, 10)
                                        .addComponent(jLabel27)))
                                .addGap(39, 39, 39))))))
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(TFnombreProp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(23, 23, 23)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel7, javax.swing.GroupLayout.PREFERRED_SIZE, 19, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(TFidProp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel8)
                            .addComponent(jComboBoxDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldDireccion1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jComboBoxDireccion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jTextFieldDireccion2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel24)
                            .addComponent(jTextFieldDireccion3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel25)
                            .addComponent(jTextFieldDireccion4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel26)
                        .addGap(2, 2, 2)
                        .addComponent(jLabel27))))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Información General"));

        jLabel3.setText("Número:");

        jLabel4.setText("Saldo:");

        TFNumeroTarjetaI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFNumeroTarjetaIActionPerformed(evt);
            }
        });

        TFsaldoI.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                TFsaldoIActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addGap(30, 30, 30)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel3)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addComponent(TFsaldoI, javax.swing.GroupLayout.DEFAULT_SIZE, 97, Short.MAX_VALUE)
                    .addComponent(TFNumeroTarjetaI))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(TFNumeroTarjetaI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(TFsaldoI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        BGuardarTarjeta.setText("Guardar tarjeta");
        BGuardarTarjeta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BGuardarTarjetaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(393, Short.MAX_VALUE))
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(BGuardarTarjeta)
                .addGap(366, 366, 366))
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addComponent(jPanel13, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(BGuardarTarjeta, javax.swing.GroupLayout.PREFERRED_SIZE, 36, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(84, Short.MAX_VALUE))
        );

        jTabbedPaneCrear.addTab("Tarjeta", jPanel10);

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneCrear, javax.swing.GroupLayout.Alignment.TRAILING)
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPaneCrear, javax.swing.GroupLayout.PREFERRED_SIZE, 486, Short.MAX_VALUE)
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jPanel1.add(jPanel5);

        jTabbedPane1.addTab("Crear", jPanel1);

        jPanelListarTarjetas.setBorder(javax.swing.BorderFactory.createTitledBorder("Listar tarjetas"));

        jButtonListarTarjetas.setText("Listar tarjetas");
        jButtonListarTarjetas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonListarTarjetasActionPerformed(evt);
            }
        });

        jTextAreaListarTarjetas.setColumns(20);
        jTextAreaListarTarjetas.setRows(5);
        jTextAreaListarTarjetas.setEnabled(false);
        jScrollPane1.setViewportView(jTextAreaListarTarjetas);

        javax.swing.GroupLayout jPanelListarTarjetasLayout = new javax.swing.GroupLayout(jPanelListarTarjetas);
        jPanelListarTarjetas.setLayout(jPanelListarTarjetasLayout);
        jPanelListarTarjetasLayout.setHorizontalGroup(
            jPanelListarTarjetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListarTarjetasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addGroup(jPanelListarTarjetasLayout.createSequentialGroup()
                .addGap(93, 93, 93)
                .addComponent(jButtonListarTarjetas)
                .addContainerGap(123, Short.MAX_VALUE))
        );
        jPanelListarTarjetasLayout.setVerticalGroup(
            jPanelListarTarjetasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListarTarjetasLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(jButtonListarTarjetas)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 280, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelListarBuses.setBorder(javax.swing.BorderFactory.createTitledBorder("Listar buses"));

        jButtonListarBuses.setText("Listar buses");
        jButtonListarBuses.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonListarBusesActionPerformed(evt);
            }
        });

        jTextAreaListarBuses.setColumns(20);
        jTextAreaListarBuses.setRows(5);
        jTextAreaListarBuses.setEnabled(false);
        jScrollPane2.setViewportView(jTextAreaListarBuses);

        javax.swing.GroupLayout jPanelListarBusesLayout = new javax.swing.GroupLayout(jPanelListarBuses);
        jPanelListarBuses.setLayout(jPanelListarBusesLayout);
        jPanelListarBusesLayout.setHorizontalGroup(
            jPanelListarBusesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListarBusesLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane2)
                .addContainerGap())
            .addGroup(jPanelListarBusesLayout.createSequentialGroup()
                .addGap(104, 104, 104)
                .addComponent(jButtonListarBuses)
                .addContainerGap(128, Short.MAX_VALUE))
        );
        jPanelListarBusesLayout.setVerticalGroup(
            jPanelListarBusesLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListarBusesLayout.createSequentialGroup()
                .addGap(36, 36, 36)
                .addComponent(jButtonListarBuses)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 277, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );

        jPanelListarRutas.setBorder(javax.swing.BorderFactory.createTitledBorder("Listar rutas\n"));

        jButtonListarRutas.setText("Listar rutas");
        jButtonListarRutas.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonListarRutasActionPerformed(evt);
            }
        });

        jTextAreaListarRutas.setColumns(20);
        jTextAreaListarRutas.setRows(5);
        jTextAreaListarRutas.setEnabled(false);
        jScrollPane3.setViewportView(jTextAreaListarRutas);

        javax.swing.GroupLayout jPanelListarRutasLayout = new javax.swing.GroupLayout(jPanelListarRutas);
        jPanelListarRutas.setLayout(jPanelListarRutasLayout);
        jPanelListarRutasLayout.setHorizontalGroup(
            jPanelListarRutasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListarRutasLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane3)
                .addContainerGap())
            .addGroup(jPanelListarRutasLayout.createSequentialGroup()
                .addGap(99, 99, 99)
                .addComponent(jButtonListarRutas)
                .addContainerGap(115, Short.MAX_VALUE))
        );
        jPanelListarRutasLayout.setVerticalGroup(
            jPanelListarRutasLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelListarRutasLayout.createSequentialGroup()
                .addGap(35, 35, 35)
                .addComponent(jButtonListarRutas)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanelListarTarjetas, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelListarBuses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanelListarRutas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelListarTarjetas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelListarBuses, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelListarRutas, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(88, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Listar", jPanel3);

        jPanelConsultarRuta1.setBorder(javax.swing.BorderFactory.createTitledBorder("Información Ruta"));

        jLabelConsultarPorCodigo1.setText("Consultar por código: ");

        jButtonConsultarRuta.setText("Consultar Ruta");
        jButtonConsultarRuta.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultarRutaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanelConsultarRuta1Layout = new javax.swing.GroupLayout(jPanelConsultarRuta1);
        jPanelConsultarRuta1.setLayout(jPanelConsultarRuta1Layout);
        jPanelConsultarRuta1Layout.setHorizontalGroup(
            jPanelConsultarRuta1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConsultarRuta1Layout.createSequentialGroup()
                .addContainerGap(27, Short.MAX_VALUE)
                .addComponent(jLabelConsultarPorCodigo1)
                .addGap(22, 22, 22)
                .addGroup(jPanelConsultarRuta1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonConsultarRuta, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxCodRuta, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanelConsultarRuta1Layout.setVerticalGroup(
            jPanelConsultarRuta1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanelConsultarRuta1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanelConsultarRuta1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabelConsultarPorCodigo1, javax.swing.GroupLayout.PREFERRED_SIZE, 27, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboBoxCodRuta, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonConsultarRuta)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel15.setBorder(javax.swing.BorderFactory.createTitledBorder("Información Tarjeta"));

        jLabel9.setText("Consultar por número:");

        jButton4.setText("Consultar Tarjeta");

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel15Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel9)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel15Layout.createSequentialGroup()
                        .addComponent(jComboBoxNumTarjeIII, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addContainerGap())))
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel9)
                    .addComponent(jComboBoxNumTarjeIII, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton4)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel16.setBorder(javax.swing.BorderFactory.createTitledBorder("Información Bus"));

        jLabel5.setText("Consultar por placa:");

        jButton3.setText("Consultar Bus");

        javax.swing.GroupLayout jPanel16Layout = new javax.swing.GroupLayout(jPanel16);
        jPanel16.setLayout(jPanel16Layout);
        jPanel16Layout.setHorizontalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel5)
                .addGap(23, 23, 23)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButton3, javax.swing.GroupLayout.DEFAULT_SIZE, 107, Short.MAX_VALUE)
                    .addComponent(jComboBoxPlacaBus, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(40, Short.MAX_VALUE))
        );
        jPanel16Layout.setVerticalGroup(
            jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel16Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel16Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel5)
                    .addComponent(jComboBoxPlacaBus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButton3)
                .addContainerGap(18, Short.MAX_VALUE))
        );

        jPanel19.setBorder(javax.swing.BorderFactory.createTitledBorder("Información Pasajeros"));

        jButtonConsultarPasajeros.setText("Consultar");
        jButtonConsultarPasajeros.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonConsultarPasajerosActionPerformed(evt);
            }
        });

        jLabel15.setText("Número de pasajeros");

        jLabel16.setText("  movilizados mensualmente");

        jLabel20.setText("Mes:");

        jLabel21.setText("Año:");

        jComboBoxInformacionPasajeros.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));

        javax.swing.GroupLayout jPanel19Layout = new javax.swing.GroupLayout(jPanel19);
        jPanel19.setLayout(jPanel19Layout);
        jPanel19Layout.setHorizontalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel19Layout.createSequentialGroup()
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel16)
                            .addGroup(jPanel19Layout.createSequentialGroup()
                                .addGap(21, 21, 21)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jLabel20)
                                    .addComponent(jLabel21))
                                .addGap(33, 33, 33)
                                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jTextFieldAnio, javax.swing.GroupLayout.PREFERRED_SIZE, 79, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jComboBoxInformacionPasajeros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(27, 27, 27)
                        .addComponent(jLabel15)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 22, Short.MAX_VALUE)
                .addComponent(jButtonConsultarPasajeros)
                .addContainerGap())
        );
        jPanel19Layout.setVerticalGroup(
            jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel19Layout.createSequentialGroup()
                        .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel16))
                    .addComponent(jButtonConsultarPasajeros, javax.swing.GroupLayout.Alignment.TRAILING))
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addGap(17, 17, 17)
                        .addComponent(jLabel20))
                    .addGroup(jPanel19Layout.createSequentialGroup()
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxInformacionPasajeros, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel19Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel21)
                    .addComponent(jTextFieldAnio, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(10, 10, 10))
        );

        jPanel20.setBorder(javax.swing.BorderFactory.createTitledBorder("Información Recargas"));

        jLabel17.setText("Valor de las recargas ");

        jLabel18.setText("realizadas mensualmente");

        jButtonRecargas.setText("Consultar");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Enero", "Febrero", "Marzo", "Abril", "Mayo", "Junio", "Julio", "Agosto", "Septiembre", "Octubre", "Noviembre", "Diciembre" }));

        jLabel22.setText("Mes:");

        jLabel23.setText("Año:");

        javax.swing.GroupLayout jPanel20Layout = new javax.swing.GroupLayout(jPanel20);
        jPanel20.setLayout(jPanel20Layout);
        jPanel20Layout.setHorizontalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel18)
                .addGap(18, 18, 18)
                .addComponent(jButtonRecargas)
                .addGap(24, 24, 24))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addComponent(jLabel17)
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addGap(41, 41, 41)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jTextField2))
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel22)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel20Layout.setVerticalGroup(
            jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel20Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jButtonRecargas)
                    .addGroup(jPanel20Layout.createSequentialGroup()
                        .addComponent(jLabel17)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel18)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jLabel22))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel20Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel23)
                    .addComponent(jTextField2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(73, 73, 73)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel15, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanelConsultarRuta1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(74, 74, 74)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel16, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(295, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(32, 32, 32)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanelConsultarRuta1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel16, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel15, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel19, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jPanel20, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 39, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Información", jPanel14);

        jPanel17.setBorder(javax.swing.BorderFactory.createTitledBorder("Recargar Tarjeta"));

        jLabel12.setText("Número de Tarjeta:");

        jLabel13.setText("Valor a recargar:");

        jButtonRecargar.setText("RECARGAR");
        jButtonRecargar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonRecargarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel17Layout = new javax.swing.GroupLayout(jPanel17);
        jPanel17.setLayout(jPanel17Layout);
        jPanel17Layout.setHorizontalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jComboBoxNumTarI, javax.swing.GroupLayout.PREFERRED_SIZE, 110, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel17Layout.createSequentialGroup()
                        .addComponent(jLabel13)
                        .addGap(18, 18, 18)
                        .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jButtonRecargar)
                            .addComponent(jTextFieldValorARecargar))))
                .addContainerGap(82, Short.MAX_VALUE))
        );
        jPanel17Layout.setVerticalGroup(
            jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel17Layout.createSequentialGroup()
                .addGap(22, 22, 22)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel12)
                    .addComponent(jComboBoxNumTarI, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel17Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel13)
                    .addComponent(jTextFieldValorARecargar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addComponent(jButtonRecargar)
                .addContainerGap(23, Short.MAX_VALUE))
        );

        jPanel18.setBorder(javax.swing.BorderFactory.createTitledBorder("Tomar un Bus"));

        jLabel14.setText("Número de Tarjeta:");

        jLabel19.setText("Bus a Tomar:");

        jComboBoxNumTarII.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBoxNumTarIIActionPerformed(evt);
            }
        });

        jButtonPagarPasaje.setText("PAGAR PASAJE");
        jButtonPagarPasaje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButtonPagarPasajeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel18Layout = new javax.swing.GroupLayout(jPanel18);
        jPanel18.setLayout(jPanel18Layout);
        jPanel18Layout.setHorizontalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(19, 19, 19)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel14)
                    .addComponent(jLabel19))
                .addGap(18, 18, 18)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jButtonPagarPasaje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxNumTarII, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jComboBoxBuses, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(38, Short.MAX_VALUE))
        );
        jPanel18Layout.setVerticalGroup(
            jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel18Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel14)
                    .addComponent(jComboBoxNumTarII, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(24, 24, 24)
                .addGroup(jPanel18Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel19)
                    .addComponent(jComboBoxBuses, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jButtonPagarPasaje)
                .addContainerGap(32, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(42, 42, 42)
                .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(36, 36, 36)
                .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(335, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(44, 44, 44)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel17, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel18, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(267, Short.MAX_VALUE))
        );

        jTabbedPane1.addTab("Usar SIT", jPanel2);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jTabbedPane1)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jTabbedPane1)
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jButtonListarTarjetasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListarTarjetasActionPerformed
        //Lista las tarjetas creadas mostrando su número, saldo, nombre, id, direccion y fecha de expedicion 
        try {
            if (tarjetas.size() == 0) {
                throw new PrimeroDebe();
            }
            String tarjetasI = "";
            for (int i = 0; i < tarjetas.size(); i++) {
                tarjetasI += "NÚMERO \t SALDO \t NOMBRE  \t ID \t DIRECCIÓN \tFECHA EXPEDICIÓN\n";
                tarjetasI += tarjetas.get(i).getNumero() + "\t";
                tarjetasI += tarjetas.get(i).getSaldo() + "\t";
                tarjetasI += tarjetas.get(i).getNombreUsuario() + "\t";
                tarjetasI += tarjetas.get(i).getIdUsuario() + "\t";
                tarjetasI += tarjetas.get(i).getDireccionUsuario() + "\n";
                tarjetasI += tarjetas.get(i).getFechaCompra() + "\n";
            }
            jTextAreaListarTarjetas.setText(tarjetasI);
        } catch (PrimeroDebe ep) {
            JOptionPane.showMessageDialog(null, "Primero debe registrar un tarjeta");
        }
    }//GEN-LAST:event_jButtonListarTarjetasActionPerformed

    private void jButtonListarBusesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListarBusesActionPerformed
        //Lista los buses creados mostrando su placa, modelo, marca, tipo, capacidad y el nombre de la ruta al cual está asignado
        try {
            if (buses.size() == 0) {
                throw new PrimeroDebe();
            }
            String buss = "";

            for (int i = 0; i < buses.size(); i++) {
                buss += "PLACA \t MODELO \t MARCA \t TIPO \t CAPACIDAD \t RUTA \n";
                buss += buses.get(i).getPlaca() + "\t";
                buss += buses.get(i).getModelo() + "\t";
                buss += buses.get(i).getMarca() + "\t";
                buss += buses.get(i).getTipo() + "\t";
                buss += buses.get(i).getCapacidad() + "\t";
                buss += buses.get(i).getRuta().getNombre() + "\n";
            }
            jTextAreaListarBuses.setText(buss);
        } catch (PrimeroDebe ep) {
            JOptionPane.showMessageDialog(null, "Primero debe registrar buses");
        }
    }//GEN-LAST:event_jButtonListarBusesActionPerformed

    private void jButtonListarRutasActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonListarRutasActionPerformed
        //Lista las rutas creadas mostrando su nombre, codigo, tipo y descripcion
        try {
            if (rutas.size() == 0) {
                throw new PrimeroDebe();
            }
            String rutass = "";
            for (int i = 0; i < rutas.size(); i++) {
                rutass += "NOMBRE \t CODIGO \t TIPO \t ORIGEN-DESTINO\n";
                rutass += rutas.get(i).getNombre() + "\t";
                rutass += rutas.get(i).getCodigo() + "\t";
                rutass += rutas.get(i).getTipo() + "\t";
                rutass += rutas.get(i).getOrigenDestino() + "\n";
            }
            jTextAreaListarRutas.setText(rutass);
        } catch (PrimeroDebe ep) {
            JOptionPane.showMessageDialog(null, "Primero debe registrar rutas");
        }
    }//GEN-LAST:event_jButtonListarRutasActionPerformed

    private void TFsaldoIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFsaldoIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFsaldoIActionPerformed

    private void TFNumeroTarjetaIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_TFNumeroTarjetaIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_TFNumeroTarjetaIActionPerformed

    private void jComboBoxRutaBusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxRutaBusActionPerformed

    }//GEN-LAST:event_jComboBoxRutaBusActionPerformed

    private void jButtonGuardarBusActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarBusActionPerformed
        //Método para crear un bus
        String placa;
        int modelo;
        String marca;
        String tipo = "";
        int indexRuta;
        int cantidadPasajeros;
        int anioActual = (int) FechaActual.get(Calendar.YEAR);
        int index = jComboBoxRutaBus.getSelectedIndex();
        try {
            if (jTextFieldPlacaLet.getText().equals("") || jTextFieldMarca.getText().equals("") || jTextFieldPlacaNum.getText().equals("")) {
                throw new CamposVacios(); 
            } else if (rutas.size() == 0) {
                throw new PrimeroDebe(); 
            } else if (soloLetras(jTextFieldPlacaLet.getText())) {
                throw new Cadena(); 
            } else if (Numeros.isNumeric(jTextFieldPlacaNum.getText()) == false) {
                throw new Numeros();
            } else if (rutas.get(index).getTipo().charAt(0) == 'E') { //Comprueba si el nombre dado a la ruta coincide con el tipo de ruta
                if (jComboBoxTipo.getSelectedIndex() != 0) {
                    throw new TipoIncorrecto();
                }
            } else if (rutas.get(index).getTipo().charAt(0) == 'T') {
                if (jComboBoxTipo.getSelectedIndex() != 1) {
                    throw new TipoIncorrecto();
                }
            } else if (rutas.get(index).getTipo().charAt(0) == 'P') {
                if (jComboBoxTipo.getSelectedIndex() != 2) {
                    throw new TipoIncorrecto();
                }
            } else if (rutas.get(index).getTipo().charAt(0) == 'A') {
                if (jComboBoxTipo.getSelectedIndex() != 3) {
                    throw new TipoIncorrecto();
                }
            }
            placa = jTextFieldPlacaLet.getText() + "-" + jTextFieldPlacaNum.getText();
            if ((int) jSpinnerAnioModelo.getValue() > (anioActual + 1)) { //Si digita un año en el que todavia no estamos

                throw new ExcepcionPersonalizada();
            }
            modelo = (int) jSpinnerAnioModelo.getValue();
            marca = jTextFieldMarca.getText();
            if (jComboBoxTipo.getSelectedIndex() == 0) {
                tipo = "Expreso";
            } else if (jComboBoxTipo.getSelectedIndex() == 1) {
                tipo = "Troncal";
            } else if (jComboBoxTipo.getSelectedIndex() == 2) {
                tipo = "Pretroncal";
            } else if (jComboBoxTipo.getSelectedIndex() == 3) {
                tipo = "Alimentador";
            }
            indexRuta = jComboBoxRutaBus.getSelectedIndex();
            if ((int) jSpinnerCantidadPasajeros.getValue() == 0) {
                JOptionPane.showMessageDialog(null, "La cantidad de pasajeros debe ser mayor a 0");
            } else {
                cantidadPasajeros = (int) jSpinnerCantidadPasajeros.getValue();
                Bus bus = new Bus(placa, modelo, marca, tipo, cantidadPasajeros, rutas.get(indexRuta));
                if (existe(bus)) {
                    throw new YaExiste();
                } else {
                    buses.add(bus);
                    file.agregarAlArchivo(bus.getPlaca() + ", " + String.valueOf(bus.getModelo()) + ", " + bus.getMarca() + ", " + bus.getTipo() + ", " + String.valueOf(bus.getCapacidad()) + ", " + rutas.get(indexRuta).getCodigo(), 2);
                }
                JOptionPane.showMessageDialog(null, "Bus guardado");
                jTextFieldPlacaLet.setText("");
                jTextFieldPlacaNum.setText("");
                jSpinnerCantidadPasajeros.setValue(1900);
                jTextFieldMarca.setText("");
                jSpinnerCantidadPasajeros.setValue(0);
            }

        } catch (ExcepcionPersonalizada ep) {
            JOptionPane.showMessageDialog(null, "Estamos en el año " + anioActual
                    + " como vas a tener un auto de ese modelo subnormal ¬¬");

        } catch (CamposVacios ep) {
            JOptionPane.showMessageDialog(null, "Los campos de placa y marca no puedes estar vacíos");
        } catch (YaExiste ep) {
            JOptionPane.showMessageDialog(null, "El bus ya ha sido registrado anteriormente");
        } catch (PrimeroDebe ep) {
            JOptionPane.showMessageDialog(null, "Primero debe agregar rutas");
        } catch (Cadena ep) {
            JOptionPane.showMessageDialog(null, "El primer campo de placa debe ser en letras");
        } catch (Numeros ep) {
            JOptionPane.showMessageDialog(null, "El segundo campo de placa debe ser en números");
        } catch (TipoIncorrecto ep) {
            JOptionPane.showMessageDialog(null, "La ruta seleccionada no concuerda con el tipo de ruta del bus seleccionada");
        }
    }//GEN-LAST:event_jButtonGuardarBusActionPerformed

    private void jButtonGuardarRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonGuardarRutaActionPerformed
        //Método para crear una ruta
        String tipoRuta = "";
        int codigo;
        String nombre;
        String descripcion;

        try {
            if (Numeros.isNumeric(jTextFieldCodigoRuta.getText()) == false) {
                throw new Numeros();
            } else if (jTextFieldCodigoRuta.getText().equals("")) {
                throw new CamposVacios();
            } else if (jTextFieldOrigen.getText().equals("")) {
                throw new CamposVacios();
            } else if (jTextFieldNombre.getText().equals("")) {
                throw new CamposVacios();
            } else if (jTextFieldNombre.getText().charAt(0) == 'A') {//Si el tipo de ruta que se le da al bus no coincide con el tipo de ruta de la ruta 
                if (jComboBoxTiposRuta.getSelectedIndex() != 3) {    //Que se le está asignando
                    throw new TipoIncorrecto();
                }
            } else if (jTextFieldNombre.getText().charAt(0) == 'E') {
                if (jComboBoxTiposRuta.getSelectedIndex() != 0) {
                    throw new TipoIncorrecto();
                }
            } else if (jTextFieldNombre.getText().charAt(0) == 'T') {
                if (jComboBoxTiposRuta.getSelectedIndex() != 1) {
                    throw new TipoIncorrecto();
                }
            } else if (jTextFieldNombre.getText().charAt(0) == 'P') {
                if (jComboBoxTiposRuta.getSelectedIndex() != 2) {
                    throw new TipoIncorrecto();
                }
            }
            if (jComboBoxTiposRuta.getSelectedIndex() == 0) {
                tipoRuta = "Expresa";
            } else if (jComboBoxTiposRuta.getSelectedIndex() == 1) {
                tipoRuta = "Troncal";
            } else if (jComboBoxTiposRuta.getSelectedIndex() == 2) {
                tipoRuta = "Pretroncal";
            } else if (jComboBoxTiposRuta.getSelectedIndex() == 3) {
                tipoRuta = "Alimentadora";
            }
            codigo = Integer.parseInt(jTextFieldCodigoRuta.getText());
            nombre = jTextFieldNombre.getText();
            descripcion = jTextFieldOrigen.getText() + "-" + jTextFieldDestino.getText();
            Ruta ruta = new Ruta(codigo, nombre, tipoRuta, descripcion);

            if (existe(ruta)) {
                throw new YaExiste();
            } else {

                rutas.add(ruta);
                file.agregarAlArchivo(String.valueOf(ruta.getCodigo()) + ", " + ruta.getNombre() + ", " + ruta.getOrigenDestino() + ", " + ruta.getTipo(), 1);
                jComboBoxCodRuta.addItem(String.valueOf(ruta.getCodigo()));
                JOptionPane.showMessageDialog(null, "Ruta guardada");
                jTextFieldCodigoRuta.setText("");
                jTextFieldOrigen.setText("");
                jTextFieldDestino.setText("");
                jTextFieldNombre.setText("");
                jComboBoxRutaBus.addItem(ruta.getNombre() + "-" + ruta.getCodigo() + " : " + ruta.getOrigenDestino());

            }

        } catch (Numeros ep) {
            JOptionPane.showMessageDialog(null, "El código debe ser en números");
        } catch (CamposVacios ep) {
            JOptionPane.showMessageDialog(null, "No puede dejar el campo de código, Origen-Destino y Nombre vacíos");
        } catch (YaExiste ep) {
            JOptionPane.showMessageDialog(null, "La ruta ya ha sido registrada anteriormente");
        } catch (TipoIncorrecto ep) {
            JOptionPane.showMessageDialog(null, "El nombre del bus no concuerda con el tipo de ruta seleccionado");
        }
    }//GEN-LAST:event_jButtonGuardarRutaActionPerformed

    private void jComboBoxTiposRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxTiposRutaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxTiposRutaActionPerformed

    private void jTextFieldPlacaLetKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextFieldPlacaLetKeyTyped
        // TODO add your handling code here:

    }//GEN-LAST:event_jTextFieldPlacaLetKeyTyped

    private void BGuardarTarjetaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BGuardarTarjetaActionPerformed
        // TODO add your handling code here:
        //Guardar tarjeta
        int numeroTar = 0;
        int saldoTar = 0;
        String direcProp = "";
        String nombreProp = "";
        int idProp = 0;
        //int dia = FechaActual.get(Calendar.DATE);
        // int mes= FechaActual.get(Calendar.MONTH);
        // int anio= FechaActual.get(Calendar.YEAR);

        try {
            if (TFNumeroTarjetaI.getText().equals("") || TFsaldoI.getText().equals("") || jTextFieldDireccion1.getText().equals("") || TFnombreProp.getText().equals("") || TFidProp.getText().equals("") || jTextFieldDireccion2.getText().equals("")
                    || jTextFieldDireccion3.getText().equals("") || jTextFieldDireccion4.getText().equals("")) {
                throw new CamposVacios();
            } else if (soloLetras(TFnombreProp.getText())) {
                throw new Cadena();
            } else if (Numeros.isNumeric(TFNumeroTarjetaI.getText()) == false || Numeros.isNumeric(TFsaldoI.getText()) == false || Numeros.isNumeric(TFidProp.getText()) == false
                    || Integer.parseInt(TFNumeroTarjetaI.getText()) < 0 || Integer.parseInt(TFsaldoI.getText()) < 0 || Integer.parseInt(TFidProp.getText()) < 0 || Numeros.isNumeric(jTextFieldDireccion1.getText()) == false
                    || Numeros.isNumeric(jTextFieldDireccion3.getText()) == false || Numeros.isNumeric(jTextFieldDireccion4.getText()) == false || Numeros.isNumeric(jTextFieldDireccion2.getText()) == false) {
                throw new Numeros();
            } else if (existeTar(Integer.parseInt(TFNumeroTarjetaI.getText()))) {
                throw new YaExiste();
            }

            Date fecha = FechaActual.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaI = sdf.format(fecha);

            numeroTar = Integer.parseInt(TFNumeroTarjetaI.getText());
            saldoTar = Integer.parseInt(TFsaldoI.getText());
            if (jComboBoxDireccion2.getSelectedItem().equals("N/A")) {
                direcProp = jComboBoxDireccion1.getSelectedItem() + " " + jTextFieldDireccion1.getText() + " # " + jTextFieldDireccion3.getText()
                        + " - " + jTextFieldDireccion4.getText();
            } else {
                direcProp = jComboBoxDireccion1.getSelectedItem() + " " + jTextFieldDireccion1.getText() + " " + jComboBoxDireccion2.getSelectedItem() + " " + jTextFieldDireccion2.getText()
                        + " # " + jTextFieldDireccion3.getText() + " - " + jTextFieldDireccion4.getText();
            }

            nombreProp = TFnombreProp.getText();
            idProp = Integer.parseInt(TFidProp.getText());

            Tarjeta tarjetaI = new Tarjeta();
            tarjetaI.setNumero(numeroTar);
            tarjetaI.setSaldo(saldoTar);
            tarjetaI.setDireccionUsuario(direcProp);
            tarjetaI.setIdUsuario(idProp);
            tarjetaI.setNombreUsuario(nombreProp);
            tarjetaI.setFechaCompra(fechaI);

            tarjetas.add(tarjetaI);
            file.agregarAlArchivo(tarjetaI.getNumero() + ", " + tarjetaI.getSaldo() + ", " + tarjetaI.getNombreUsuario() + ", " + tarjetaI.getDireccionUsuario() + ", " + tarjetaI.getIdUsuario() + ", " + tarjetaI.getFechaCompra(), 3);
            JOptionPane.showMessageDialog(null, "Tarjeta numero " + numeroTar + " ha sido registrada");
            TFNumeroTarjetaI.setText(null);
            TFsaldoI.setText(null);
            TFnombreProp.setText(null);
            TFidProp.setText(null);
            jTextFieldDireccion1.setText(null);
            jTextFieldDireccion2.setText(null);
            jTextFieldDireccion3.setText(null);
            jTextFieldDireccion4.setText(null);

            jComboBoxNumTarI.addItem(String.valueOf(tarjetaI.getNumero()));
            jComboBoxNumTarII.addItem(String.valueOf(tarjetaI.getNumero()));
            jComboBoxNumTarjeIII.addItem(String.valueOf(tarjetaI.getNumero()));

        } catch (CamposVacios ep) {
            JOptionPane.showMessageDialog(null, "Debe llenar todos los campos");
        } catch (Cadena e) {
            JOptionPane.showMessageDialog(null, "El nombre del propietario es un campo que solo debe llenar con letras");
        } catch (Numeros ex) {
            JOptionPane.showMessageDialog(null, "En el campo de Número de Tarjeta,ID, Saldo sólo deben ir números enteros y positivos y en los de campos de dirección sólo pueden ir números");
        } catch (YaExiste eI) {
            JOptionPane.showMessageDialog(null, "Esa tarjeta ya existe");
        }
    }//GEN-LAST:event_BGuardarTarjetaActionPerformed

    private void jButtonRecargarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonRecargarActionPerformed
        // TODO add your handling code here:
        try {
            if (jTextFieldValorARecargar.getText().equals("")) {
                throw new CamposVacios();
            }
            if (jComboBoxNumTarI.getSelectedIndex() == -1) {
                throw new NoExiste();
            }
            if ((Numeros.isNumeric(jTextFieldValorARecargar.getText()) == false || Integer.parseInt(jTextFieldValorARecargar.getText()) < 0)) {
                throw new Numeros();
            }

            int valor = Integer.parseInt(jTextFieldValorARecargar.getText());
            int saldoNue = 0;
            Date fecha = FechaActual.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaII = sdf.format(fecha);
            String acumulador = "";

            for (int i = 0; i < tarjetas.size(); i++) {
                Tarjeta tI = (Tarjeta) tarjetas.get(i);

                if (i == jComboBoxNumTarI.getSelectedIndex()) {
                    saldoNue = tI.getSaldo() + valor;
                    tI.setSaldo(saldoNue);
                    fechasRecargas.add(fechaII);
                    tI.setFechasRecargas(fechaII);
                    file.agregarAlArchivo(fechaII + ", " + valor, 4);
                }
            }
            for (int i = 0; i < tarjetas.size(); i++) {
                if (tarjetas.get(i).getNumero() == tarjetas.get(jComboBoxNumTarI.getSelectedIndex()).getNumero()) {
                    tarjetas.get(i).setSaldo(saldoNue);
                    acumulador += tarjetas.get(i).getNumero() + ", " + tarjetas.get(i).getSaldo() + ", " + tarjetas.get(i).getNombreUsuario() + ", " + tarjetas.get(i).getDireccionUsuario() + ", " + tarjetas.get(i).getIdUsuario() + ", " + tarjetas.get(i).getFechaCompra() + "\n";
                } else {
                    acumulador += tarjetas.get(i).getNumero() + ", " + tarjetas.get(i).getSaldo() + ", " + tarjetas.get(i).getNombreUsuario() + ", " + tarjetas.get(i).getDireccionUsuario() + ", " + tarjetas.get(i).getIdUsuario() + ", " + tarjetas.get(i).getFechaCompra() + "\n";
                }
            }

            file.agregarAlArchivo(acumulador, 6);

            JOptionPane.showMessageDialog(null, "Su nuevo saldo es " + saldoNue);
            jTextFieldValorARecargar.setText(null);
        } catch (CamposVacios ep) {
            JOptionPane.showMessageDialog(null, "Debe llenar el campo con el valor a recargar");
        } catch (NoExiste el) {
            JOptionPane.showMessageDialog(null, "Debe crear una tarjeta para recargarla");
        } catch (Numeros ex) {
            JOptionPane.showMessageDialog(null, "Solo puede escribir números enteros y positivos");
        }


    }//GEN-LAST:event_jButtonRecargarActionPerformed

    private void jButtonPagarPasajeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonPagarPasajeActionPerformed
        try {
            if (jComboBoxNumTarII.getSelectedIndex() == -1 || jComboBoxBuses.getSelectedIndex() == -1) {
                throw new NoExiste();
            }

            int saldoNue = 0;
            Date fecha = FechaActual.getTime();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String fechaIII = sdf.format(fecha);
            for (int i = 0; i < tarjetas.size(); i++) {
                if (i == jComboBoxNumTarII.getSelectedIndex()) {
                    Tarjeta tI = (Tarjeta) tarjetas.get(i);
                    if (tI.getSaldo() - 2000 < -2000) {
                        throw new ExcepcionPersonalizada();
                    } else {
                        saldoNue = tI.getSaldo() - 2000;
                        tI.setSaldo(saldoNue);
                        tI.setFechasExtracciones(fechaIII);
                    }
                }
            }
            file.agregarAlArchivo(jComboBoxNumTarII.getSelectedItem() + ", " + fechaIII, 5);
            fechasDescargas.add(jComboBoxNumTarII.getSelectedItem() + ", " + fechaIII);
            JOptionPane.showMessageDialog(null, "Su nuevo saldo es " + saldoNue);
        } catch (NoExiste ej) {
            JOptionPane.showMessageDialog(null, "Debe crear una tarjeta y un bus para poder gastar pasajes");
        } catch (ExcepcionPersonalizada ep){
            JOptionPane.showMessageDialog(null, "No puede gastar más pasajes, de hecho tiene una deuda de $2000");
        }
    }//GEN-LAST:event_jButtonPagarPasajeActionPerformed

    private void jButtonConsultarRutaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarRutaActionPerformed
        int codigo = Integer.parseInt((String) jComboBoxCodRuta.getSelectedItem());
        String acumulador = "";
        for (int i = 0; i < rutas.size(); i++) {
            if (codigo == rutas.get(i).getCodigo()) {
                acumulador += "Código: " + String.valueOf(rutas.get(i).getCodigo()) + "\n";
                acumulador += "Nombre: " + rutas.get(i).getNombre() + "\n";
                acumulador += "Descripción: " + rutas.get(i).getOrigenDestino() + "\n";
                acumulador += "Tipo: " + rutas.get(i).getTipo();
            }
        }
        JOptionPane.showMessageDialog(null, acumulador);
    }//GEN-LAST:event_jButtonConsultarRutaActionPerformed

    private void jComboBoxNumTarIIActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBoxNumTarIIActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_jComboBoxNumTarIIActionPerformed

    private void jButtonConsultarPasajerosActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButtonConsultarPasajerosActionPerformed
        //Método para hacer una consulta de pasajeros por mes
        try {
            if (jTextFieldAnio.getText().equals("")) {
                throw new CamposVacios();
            } else if (Integer.parseInt(jTextFieldAnio.getText()) <= 0) {
                throw new NumerosPosi();
            } else if (Numeros.isNumeric(jTextFieldAnio.getText()) == false) {
                throw new Numeros();
            }
            int contador = 0;
            int mesDefi = 0;
            int anioDefi = 0;
            LeerArchivo arch = new LeerArchivo();
            arch.readFile("fechasExtracciones.txt", 5);
            for (int i = 0; i < arch.getFechasDescargas().size(); i++) {
                if ((i % 2) == 1) {//La posicion que necesitamos es par
                    String fecha1 = arch.getFechasDescargas().get(i);
                    String mes = fecha1.charAt(6) + "";
                    mes += fecha1.charAt(7) + "";
                    String anio = fecha1.charAt(1) + "";
                    anio += fecha1.charAt(2) + "";
                    anio += fecha1.charAt(3) + "";
                    anio += fecha1.charAt(4) + "";
                    mesDefi = Integer.parseInt(mes);
                    anioDefi = Integer.parseInt(anio);
                    if (Integer.parseInt(jTextFieldAnio.getText()) == anioDefi) {
                        if ((jComboBoxInformacionPasajeros.getSelectedIndex() + 1) == mesDefi) {
                            contador++;
                        } else {
                            throw new NoExiste();
                        }
                    } else {
                        throw new ExcepcionPersonalizada();
                    }
                }
            }
            JOptionPane.showMessageDialog(null, "Cantidad de pasajeros que han viajado en el año: " + anioDefi + " mes: " + jComboBoxInformacionPasajeros.getSelectedItem() + " Son: " + contador);
        } catch (NoExiste ep) {
            JOptionPane.showMessageDialog(null, "No hay datos del mes " + jComboBoxInformacionPasajeros.getSelectedItem() + " del " + jTextFieldAnio.getText());
        } catch (ExcepcionPersonalizada ep) {
            JOptionPane.showMessageDialog(null, "No hay datos del año " + jTextFieldAnio.getText());
        } catch (CamposVacios ep) {
            JOptionPane.showMessageDialog(null, "No puede dejar el campo del año vacío");
        } catch (NumerosPosi ep) {
            JOptionPane.showMessageDialog(null, "No puede digitar un año 0 o menor a 0");
        } catch (Numeros ep) {
            JOptionPane.showMessageDialog(null, "El campo del año sólo puede ser en números");
        }

    }//GEN-LAST:event_jButtonConsultarPasajerosActionPerformed

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
            java.util.logging.Logger.getLogger(CRUD2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(CRUD2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(CRUD2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(CRUD2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new CRUD2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BGuardarTarjeta;
    private javax.swing.JTextField TFNumeroTarjetaI;
    private javax.swing.JTextField TFidProp;
    private javax.swing.JTextField TFnombreProp;
    private javax.swing.JTextField TFsaldoI;
    private javax.swing.ButtonGroup buttonGroup1;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JButton jButtonConsultarPasajeros;
    private javax.swing.JButton jButtonConsultarRuta;
    private javax.swing.JButton jButtonGuardarBus;
    private javax.swing.JButton jButtonGuardarRuta;
    private javax.swing.JButton jButtonListarBuses;
    private javax.swing.JButton jButtonListarRutas;
    private javax.swing.JButton jButtonListarTarjetas;
    private javax.swing.JButton jButtonPagarPasaje;
    private javax.swing.JButton jButtonRecargar;
    private javax.swing.JButton jButtonRecargas;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JComboBox<String> jComboBoxBuses;
    private javax.swing.JComboBox<String> jComboBoxCodRuta;
    private javax.swing.JComboBox<String> jComboBoxDireccion1;
    private javax.swing.JComboBox<String> jComboBoxDireccion2;
    private javax.swing.JComboBox<String> jComboBoxInformacionPasajeros;
    private javax.swing.JComboBox<String> jComboBoxNumTarI;
    private javax.swing.JComboBox<String> jComboBoxNumTarII;
    private javax.swing.JComboBox<String> jComboBoxNumTarjeIII;
    private javax.swing.JComboBox<String> jComboBoxPlacaBus;
    private javax.swing.JComboBox<String> jComboBoxRutaBus;
    private javax.swing.JComboBox<String> jComboBoxTipo;
    private javax.swing.JComboBox<String> jComboBoxTiposRuta;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JLabel jLabelCapacidadPasajeros;
    private javax.swing.JLabel jLabelCodigoRuta;
    private javax.swing.JLabel jLabelConsultarPorCodigo1;
    private javax.swing.JLabel jLabelInformacion1;
    private javax.swing.JLabel jLabelInformacion2;
    private javax.swing.JLabel jLabelMarca;
    private javax.swing.JLabel jLabelModelo;
    private javax.swing.JLabel jLabelNombre;
    private javax.swing.JLabel jLabelOrigenDestino;
    private javax.swing.JLabel jLabelPlaca;
    private javax.swing.JLabel jLabelTipo;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel16;
    private javax.swing.JPanel jPanel17;
    private javax.swing.JPanel jPanel18;
    private javax.swing.JPanel jPanel19;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel20;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JPanel jPanelConsultarRuta1;
    private javax.swing.JPanel jPanelListarBuses;
    private javax.swing.JPanel jPanelListarRutas;
    private javax.swing.JPanel jPanelListarTarjetas;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JSpinner jSpinnerAnioModelo;
    private javax.swing.JSpinner jSpinnerCantidadPasajeros;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTabbedPane jTabbedPaneCrear;
    private javax.swing.JTextArea jTextAreaListarBuses;
    private javax.swing.JTextArea jTextAreaListarRutas;
    private javax.swing.JTextArea jTextAreaListarTarjetas;
    private javax.swing.JTextField jTextField2;
    private javax.swing.JTextField jTextFieldAnio;
    private javax.swing.JTextField jTextFieldCodigoRuta;
    private javax.swing.JTextField jTextFieldDestino;
    private javax.swing.JTextField jTextFieldDireccion1;
    private javax.swing.JTextField jTextFieldDireccion2;
    private javax.swing.JTextField jTextFieldDireccion3;
    private javax.swing.JTextField jTextFieldDireccion4;
    private javax.swing.JTextField jTextFieldMarca;
    private javax.swing.JTextField jTextFieldNombre;
    private javax.swing.JTextField jTextFieldOrigen;
    private javax.swing.JTextField jTextFieldPlacaLet;
    private javax.swing.JTextField jTextFieldPlacaNum;
    private javax.swing.JTextField jTextFieldValorARecargar;
    // End of variables declaration//GEN-END:variables
}
