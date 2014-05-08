/*
NAME:	XIAOYANG JU
ID:	7342138151
*/

import java.awt.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;
import javax.swing.*;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import oracle.jdbc.OracleResultSet;
import java.awt.event.MouseMotionAdapter;


@SuppressWarnings("serial")
public class hw2 extends JFrame{

	public static final int WHOLE_REGION = 1;
	public static final int RANGE_QUERY = 2;
	public static final int POINT_QUERY = 3;
	public static final int SURROUNDING_STUDENT = 4;  
	public static final int EMERGENCY_QUERY = 5;

	private boolean firstClick_ON_Map = false;
	private int Query_Num = 0;
	private int clicked_x;
	private int clicked_y;
	private int i=0;
	ArrayList<Integer> left_mouse_X = new ArrayList<Integer>();
	ArrayList<Integer> left_mouse_Y = new ArrayList<Integer>();

	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JPanel panel;
	private JPanel panel_1;
	private JPanel panel_2;
	private JCheckBox chckbxAs;
	private JCheckBox chckbxBuildings;
	private JCheckBox chckbxStudents;
	private JRadioButton rdbtnWholeRegion;
	private JRadioButton rdbtnPointQuery;
	private JRadioButton rdbtnRangeQuery;
	private JRadioButton rdbtnSurroundingStudent;
	private JRadioButton rdbtnEmergencyQuery;
	private JLabel lblNewLabel_1;
	private JLabel lblNewLabel_2;
	private JLabel lblNewLabel;
	private JButton btnNewButton;
	private GroupLayout gl_panel_1;
	private GroupLayout groupLayout;
	private GroupLayout gl_panel_2;
	private GroupLayout gl_panel;
	private JTextField mouseMove;
	private JScrollPane scrollPane;
	private JScrollPane scrollPane_1;
	private JTextArea textArea;
	private JLabel lblCurrentMouseLocation;


	public hw2() {
		this.setTitle("XIAOYANG JU  7342138151");
		this.setBounds(100, 100, 1087, 800);
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		initializeGUI();
	}


	/* ---------------Initialize the GUI------------------- */
	private void initializeGUI() {
		panel = new JPanel();
		panel.setToolTipText("");
		panel_1 = new JPanel();

		chckbxAs = new JCheckBox("AS");
		chckbxBuildings = new JCheckBox("BUILDINGS");
		chckbxStudents = new JCheckBox("STUDENTS");

		rdbtnWholeRegion = new JRadioButton("Whole Region");
		rdbtnWholeRegion.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel.repaint();
			}
		});
		rdbtnWholeRegion.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		buttonGroup.add(rdbtnWholeRegion);

		rdbtnPointQuery = new JRadioButton("Point Query");
		rdbtnPointQuery.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel.repaint();
			}
		});
		rdbtnPointQuery.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		buttonGroup.add(rdbtnPointQuery);

		rdbtnRangeQuery = new JRadioButton("Range Query");
		rdbtnRangeQuery.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel.repaint();
			}
		});
		rdbtnRangeQuery.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		buttonGroup.add(rdbtnRangeQuery);

		rdbtnSurroundingStudent = new JRadioButton("Surrounding Student");
		rdbtnSurroundingStudent.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel.repaint();
			}
		});
		rdbtnSurroundingStudent.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		buttonGroup.add(rdbtnSurroundingStudent);

		rdbtnEmergencyQuery = new JRadioButton("Emergency Query");
		rdbtnEmergencyQuery.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNewLabel.repaint();
			}
		});
		rdbtnEmergencyQuery.setFont(new Font("Lucida Grande", Font.PLAIN, 17));
		buttonGroup.add(rdbtnEmergencyQuery);

		lblNewLabel_1 = new JLabel("Active Feature Type");
		lblNewLabel_1.setFont(new Font("Lucida Grande", Font.PLAIN, 20));

		lblNewLabel_2 = new JLabel("Query");
		lblNewLabel_2.setFont(new Font("Lucida Grande", Font.PLAIN, 20));

		btnNewButton = new JButton("Submit            Query");
		btnNewButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				String circle;
				String rangeArea;
				int queryType = getQueryType();
				ArrayList<String> checked = getChecked();
				switch(queryType) {
				case WHOLE_REGION:  
					for(String feature : checked) {
						if(feature.equalsIgnoreCase("BUILDINGS")) {
							wholeRegion_BUILDINGS();
						}
						if(feature.equalsIgnoreCase("STUDENTS")) {
							wholeRegion_STUDENTS();
						}
						if(feature.equalsIgnoreCase("ANNOUNCEMENTS")) {
							wholeRegion_ANNOUNCEMENTS();
						}
					}
					break;
				case POINT_QUERY:
					circle= PQ_threePoints();
					for(String feature : checked) {
						if(feature.equalsIgnoreCase("BUILDINGS")) {
							POINT_QUERY_BUILDINGS(circle);
						}
						if(feature.equalsIgnoreCase("STUDENTS")) {
							POINT_QUERY_STUDENTS(circle);
						}
						if(feature.equalsIgnoreCase("ANNOUNCEMENTS")) {
							POINT_QUERY_ANNOUNCEMENTS(circle);
						}
					}
					break;
				case RANGE_QUERY:
					rangeArea=queryRange();
					for(String feature : checked) {
						if(feature.equalsIgnoreCase("BUILDINGS")) {
							RANGE_QUERY_BUILDINGS(rangeArea);
						}
						if(feature.equalsIgnoreCase("STUDENTS")) {
							RANGE_QUERY_STUDENTS(rangeArea);
						}
						if(feature.equalsIgnoreCase("ANNOUNCEMENTS")) {
							RANGE_QUERY_ANNOUNCEMENTS(rangeArea);
						}
					}
					break;
				case SURROUNDING_STUDENT:
					SURROUNDING_STUDENT();
					break;
				case EMERGENCY_QUERY:
					try {
						EMERGENCY_QUERY();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					break;
				}
			}});

		btnNewButton.setFont(new Font("Lucida Grande", Font.PLAIN, 17));

		mouseMove = new JTextField();
		mouseMove.setColumns(10);

		lblCurrentMouseLocation = new JLabel("Current mouse location");
		gl_panel_1 = new GroupLayout(panel_1);
		gl_panel_1.setHorizontalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
								.addGroup(gl_panel_1.createSequentialGroup()
										.addGap(15)
										.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
												.addComponent(rdbtnWholeRegion)
												.addComponent(rdbtnPointQuery)
												.addComponent(rdbtnRangeQuery)
												.addComponent(rdbtnSurroundingStudent)
												.addComponent(rdbtnEmergencyQuery)))
												.addGroup(gl_panel_1.createSequentialGroup()
														.addGap(6)
														.addGroup(gl_panel_1.createParallelGroup(Alignment.TRAILING)
																.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING, false)
																		.addGroup(gl_panel_1.createSequentialGroup()
																				.addPreferredGap(ComponentPlacement.RELATED)
																				.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 250, GroupLayout.PREFERRED_SIZE))
																				.addGroup(gl_panel_1.createSequentialGroup()
																						.addGap(24)
																						.addComponent(lblNewLabel_2, GroupLayout.DEFAULT_SIZE, GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
																						.addGroup(gl_panel_1.createParallelGroup(Alignment.LEADING)
																								.addComponent(chckbxAs)
																								.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 236, GroupLayout.PREFERRED_SIZE)
																								.addComponent(chckbxBuildings)
																								.addComponent(chckbxStudents))))
																								.addGroup(gl_panel_1.createSequentialGroup()
																										.addContainerGap()
																										.addComponent(mouseMove, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
																										.addGroup(gl_panel_1.createSequentialGroup()
																												.addContainerGap()
																												.addComponent(lblCurrentMouseLocation)))
																												.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);
		gl_panel_1.setVerticalGroup(
				gl_panel_1.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_1.createSequentialGroup()
						.addGap(44)
						.addComponent(lblNewLabel_1, GroupLayout.PREFERRED_SIZE, 28, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(chckbxAs)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(chckbxBuildings)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(chckbxStudents)
						.addPreferredGap(ComponentPlacement.RELATED, 45, Short.MAX_VALUE)
						.addComponent(lblNewLabel_2, GroupLayout.PREFERRED_SIZE, 31, GroupLayout.PREFERRED_SIZE)
						.addGap(18)
						.addComponent(rdbtnWholeRegion)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(rdbtnPointQuery)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(rdbtnRangeQuery)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(rdbtnSurroundingStudent)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(rdbtnEmergencyQuery)
						.addGap(18)
						.addComponent(btnNewButton, GroupLayout.PREFERRED_SIZE, 71, GroupLayout.PREFERRED_SIZE)
						.addGap(32)
						.addComponent(lblCurrentMouseLocation)
						.addPreferredGap(ComponentPlacement.RELATED)
						.addComponent(mouseMove, GroupLayout.PREFERRED_SIZE, GroupLayout.DEFAULT_SIZE, GroupLayout.PREFERRED_SIZE))
				);
		panel_1.setLayout(gl_panel_1);

		lblNewLabel = new JLabel("");
		lblNewLabel.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent e) {
				mouseMove.setText("X="+e.getX()+",Y="+e.getY());
			}
			@Override
			public void mouseExited(MouseEvent e) {
			}
			@Override
			public void mouseClicked(MouseEvent e) {
				if(firstClick_ON_Map)
				{
					lblNewLabel.repaint();
					left_mouse_X = new ArrayList<Integer>();
					left_mouse_Y = new ArrayList<Integer>();
					i=0;
					firstClick_ON_Map = false;
				}
				else	
				{
					if(rdbtnPointQuery.isSelected())
					{
						Graphics g = lblNewLabel.getGraphics();
						g.setColor(Color.red);
						clicked_x=e.getX();
						clicked_y=e.getY();
						g.fillRect(clicked_x-5/2, clicked_y-5/2, 5, 5);
						g.drawOval(clicked_x-50, clicked_y-50, 100, 100);
						firstClick_ON_Map = true;
					}
					if(rdbtnSurroundingStudent.isSelected())
					{
						nearestAS(e);
						firstClick_ON_Map = true;
					}

					if(rdbtnEmergencyQuery.isSelected()){
						nearestAS(e);
						firstClick_ON_Map = true;
					}

					if(rdbtnRangeQuery.isSelected()){
						Graphics g = lblNewLabel.getGraphics();
						g.setColor(Color.red);
						clicked_x=e.getX();
						clicked_y=e.getY();
						if(e.getButton()==1&&i==0)
						{
							g.fillRect(clicked_x-5/2, clicked_y-5/2, 5, 5);
							left_mouse_X.add(clicked_x);
							left_mouse_Y.add(clicked_y);
							i++;
						}
						else if(e.getButton()==1&&i!=0)
						{
							g.fillRect(clicked_x-5/2, clicked_y-5/2, 5, 5);
							left_mouse_X.add(clicked_x);
							left_mouse_Y.add(clicked_y);
							g.drawLine(left_mouse_X.get(i-1), left_mouse_Y.get(i-1), left_mouse_X.get(i), left_mouse_Y.get(i));
							i++;
						}

						if(e.getButton()==3)
						{
							g.drawLine(left_mouse_X.get(i-1), left_mouse_Y.get(i-1), left_mouse_X.get(0), left_mouse_Y.get(0));
							firstClick_ON_Map = true;

						}
					}
				}
			}
		});
		lblNewLabel.addMouseMotionListener(new MouseMotionAdapter() {
			@Override
			public void mouseMoved(MouseEvent e) {
				mouseMove.setText("X="+e.getX()+",Y="+e.getY());
			}
		});
		lblNewLabel.setIcon(new ImageIcon("map.jpg"));

		panel_2 = new JPanel();
		groupLayout = new GroupLayout(this.getContentPane());
		groupLayout.setHorizontalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING)
								.addGroup(groupLayout.createSequentialGroup()
										.addComponent(panel, GroupLayout.PREFERRED_SIZE, 820, GroupLayout.PREFERRED_SIZE)
										.addPreferredGap(ComponentPlacement.RELATED)
										.addComponent(panel_1, GroupLayout.PREFERRED_SIZE, 260, Short.MAX_VALUE))
										.addComponent(panel_2, GroupLayout.PREFERRED_SIZE, 1086, GroupLayout.PREFERRED_SIZE))
										.addContainerGap())
				);
		groupLayout.setVerticalGroup(
				groupLayout.createParallelGroup(Alignment.LEADING)
				.addGroup(groupLayout.createSequentialGroup()
						.addGroup(groupLayout.createParallelGroup(Alignment.LEADING, false)
								.addComponent(panel_1, 0, 0, Short.MAX_VALUE)
								.addComponent(panel, GroupLayout.DEFAULT_SIZE, 580, Short.MAX_VALUE))
								.addPreferredGap(ComponentPlacement.RELATED)
								.addComponent(panel_2, GroupLayout.DEFAULT_SIZE, 186, Short.MAX_VALUE)
								.addContainerGap())
				);
		scrollPane = new JScrollPane();

		scrollPane_1 = new JScrollPane();

		gl_panel_2 = new GroupLayout(panel_2);
		gl_panel_2.setHorizontalGroup(
				gl_panel_2.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel_2.createSequentialGroup()
						.addComponent(scrollPane_1, GroupLayout.DEFAULT_SIZE, 1080, Short.MAX_VALUE)
						.addContainerGap())
				);
		gl_panel_2.setVerticalGroup(
				gl_panel_2.createParallelGroup(Alignment.TRAILING)
				.addGroup(Alignment.LEADING, gl_panel_2.createSequentialGroup()
						.addComponent(scrollPane_1, GroupLayout.PREFERRED_SIZE, 183, GroupLayout.PREFERRED_SIZE)
						.addContainerGap(GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
				);

		textArea = new JTextArea();
		textArea.setLineWrap(true);
		scrollPane_1.setViewportView(textArea);
		panel_2.setLayout(gl_panel_2);
		gl_panel = new GroupLayout(panel);
		gl_panel.setHorizontalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addComponent(lblNewLabel)
						.addContainerGap(820, Short.MAX_VALUE))
				);
		gl_panel.setVerticalGroup(
				gl_panel.createParallelGroup(Alignment.LEADING)
				.addGroup(gl_panel.createSequentialGroup()
						.addGap(5)
						.addComponent(lblNewLabel)
						.addContainerGap(575, Short.MAX_VALUE))
				);
		panel.setLayout(gl_panel);
		this.getContentPane().setLayout(groupLayout);
	}

	public static void main(String[] args) {
		Window window = new hw2();
		window.setVisible(true);
	}


	/* ---------------RADIOBUTTON RESULT------------------- */
	public int getQueryType() {
		if (rdbtnWholeRegion.isSelected()) {
			return WHOLE_REGION;
		}
		if (rdbtnRangeQuery.isSelected()) {
			return RANGE_QUERY;
		}
		if (rdbtnPointQuery.isSelected()) {
			return POINT_QUERY;
		}
		if (rdbtnSurroundingStudent.isSelected()) {
			return SURROUNDING_STUDENT;
		}
		if (rdbtnEmergencyQuery.isSelected()) {
			return EMERGENCY_QUERY;
		}
		return 0;
	}


	/* ---------------CHECKBOX RESULT------------------- */
	public ArrayList<String> getChecked() {
		ArrayList<String> checked = new ArrayList<String>();
		if (chckbxStudents.isSelected()) {
			checked.add("STUDENTS");
		}
		if (chckbxAs.isSelected()) {
			checked.add("ANNOUNCEMENTS");
		}
		if (chckbxBuildings.isSelected()) {
			checked.add("BUILDINGS");
		}
		return checked;
	}



	/* ---------------Query JText update------------------- */
	public void updateJText(String query) {
		Query_Num++;
		textArea.setText(textArea.getText() + "Query "+Query_Num+": "+ query + "\n");
	}

	
	/* ---------------HIGHLIGHT THE NEAREST AS------------------- */
	public void nearestAS(MouseEvent e){
		Graphics g = lblNewLabel.getGraphics();
		g.setColor(Color.red);
		clicked_x=e.getX();
		clicked_y=e.getY();
		g.fillRect(clicked_x-5/2, clicked_y-5/2, 5, 5);
		DBexecute db = new DBexecute();
		db.connect();
		String query = "SELECT a.LOC.SDO_POINT.x, a.LOC.SDO_POINT.y, a.RADIUS   FROM ANNOUNCEMENTSYSTEMS a "
				+ "WHERE SDO_NN(a.AREA, SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE("
				+ clicked_x + ", " + clicked_y + ", NULL), NULL, NULL), 'sdo_num_res=1') = 'TRUE'"; 
		ResultSet rsp = db.getResultSet(query);
		draw_ANNOUNCEMENTS(rsp,Color.red);
		db.close();
	}

	
	/* ---------------CIRCLE_THREE_POINTS------------------- */
	public String PQ_threePoints() {
		String circle= (clicked_x) + "," + (clicked_y+50) + "," + (clicked_x+50) + "," + (clicked_y) + "," + (clicked_x) + "," + (clicked_y-50);
		return circle;
	}
	
	
	/* ---------------GET QUERY RANGE------------------- */
	public String queryRange(){
		String result = "";
		for(int j=0;j<left_mouse_X.size();j++)
		{
			result += left_mouse_X.get(j) + ","+ left_mouse_Y.get(j) + ",";
		}
		result += left_mouse_X.get(0) + ","+ left_mouse_Y.get(0);
		return result;
	}

	
	/* ---------------WHOLE REGION STUDENTS------------------- */
	public void wholeRegion_STUDENTS() {
		DBexecute db = new DBexecute();
		db.connect();
		String query = "SELECT s.LOC.SDO_POINT.x,s.LOC.SDO_POINT.y FROM STUDENTS s";
		updateJText(query);  
		ResultSet rsp = db.getResultSet(query);   
		draw_STUDENTS(rsp,Color.green);
		db.close();
	}


	/* ---------------POINT_QUERY_STUDENTS------------------- */
	public void POINT_QUERY_STUDENTS(String circle) {
		DBexecute db = new DBexecute();
		db.connect();
		String query = "SELECT s.LOC.SDO_POINT.x,s.LOC.SDO_POINT.y FROM STUDENTS s WHERE SDO_INSIDE(s.LOC,SDO_GEOMETRY(2003, NULL, NULL, "
				+ "SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY(" + circle + "))) = 'TRUE'"
				+ "ORDER BY SDO_GEOM.SDO_DISTANCE(s.LOC, SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE("
				+ clicked_x + ", " + clicked_y + ", NULL), NULL, NULL), 1)";
		updateJText(query);  
		ResultSet rsp = db.getResultSet(query);   
		draw_STUDENTS(rsp,Color.green);
		db.close();
	}


	/* ---------------RANGE_QUERY_STUDENTS------------------- */
	public void RANGE_QUERY_STUDENTS(String rangeArea){
		DBexecute db = new DBexecute();
		db.connect();
		String query = "SELECT s.LOC.SDO_POINT.x,s.LOC.SDO_POINT.y FROM STUDENTS s WHERE SDO_INSIDE(s.LOC, SDO_GEOMETRY(2003, NULL, NULL, "
				+ "SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY(" + rangeArea + "))) = 'TRUE'";
		updateJText(query);
		ResultSet rsp = db.getResultSet(query);   
		draw_STUDENTS(rsp,Color.green);
		db.close();
	}


	/* ---------------SURROUNDING_STUDENTS------------------- */
	public void SURROUNDING_STUDENT(){
		DBexecute db = new DBexecute();
		db.connect();
		String query = "SELECT s.LOC.SDO_POINT.x,s.LOC.SDO_POINT.y   FROM ANNOUNCEMENTSYSTEMS a,STUDENTS s WHERE SDO_NN(a.AREA, SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE("
				+ clicked_x + ", " + clicked_y + ", NULL), NULL, NULL), 'sdo_num_res=1') = 'TRUE' AND SDO_INSIDE(s.LOC, a.AREA) = 'TRUE'";
		updateJText(query);  
		ResultSet rsp = db.getResultSet(query);   
		draw_STUDENTS(rsp,Color.green);
		db.close();
	}


	/* ---------------DRAW_____STUDENTS------------------- */
	public void draw_STUDENTS(ResultSet rs, Color c){
		try {
			boolean firstone = false;
			while(rs.next())
			{
				Graphics g = lblNewLabel.getGraphics();
				if(rdbtnPointQuery.isSelected()&&!firstone)
				{
					g.setColor(Color.yellow);
					firstone = true;
				}
				else  g.setColor(c);
				int x = rs.getInt("LOC.SDO_POINT.x");
				int y = rs.getInt("LOC.SDO_POINT.y");
				g.fillRect(x-5, y-5, 10, 10);
				g.dispose();
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/* ---------------WHOLE REGION ANNOUNCEMENTS------------------- */
	public void wholeRegion_ANNOUNCEMENTS() {
		DBexecute db = new DBexecute();
		db.connect();
		String query = "SELECT a.LOC.SDO_POINT.x, a.LOC.SDO_POINT.y, a.RADIUS   FROM ANNOUNCEMENTSYSTEMS a";
		updateJText(query); 
		ResultSet rsp = db.getResultSet(query);
		draw_ANNOUNCEMENTS(rsp,Color.red);
		db.close();
	}

	
	/* ---------------POINT_QUERY_ANNOUNCEMENTS------------------- */
	public void POINT_QUERY_ANNOUNCEMENTS(String circle){
		DBexecute db = new DBexecute();
		db.connect();
		String query = "SELECT a.LOC.SDO_POINT.x, a.LOC.SDO_POINT.y, a.RADIUS   FROM ANNOUNCEMENTSYSTEMS a WHERE SDO_ANYINTERACT(a.AREA,SDO_GEOMETRY(2003, NULL, NULL, "
				+ "SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY(" + circle + "))) = 'TRUE'"
				+ "ORDER BY SDO_GEOM.SDO_DISTANCE(a.AREA, SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE("
				+ clicked_x + ", " + clicked_y + ", NULL), NULL, NULL), 1)";
		updateJText(query); 
		ResultSet rsp = db.getResultSet(query);
		draw_ANNOUNCEMENTS(rsp,Color.green);
		db.close();
	}


	/* ---------------RANGE_QUERY_ANNOUNCEMENTS------------------- */
	public void RANGE_QUERY_ANNOUNCEMENTS(String rangeArea){
		DBexecute db = new DBexecute();
		db.connect();
		String query = "SELECT a.LOC.SDO_POINT.x, a.LOC.SDO_POINT.y, a.RADIUS   FROM ANNOUNCEMENTSYSTEMS a "
				+ "WHERE SDO_ANYINTERACT(a.AREA, SDO_GEOMETRY(2003, NULL, NULL, "
				+ "SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY(" + rangeArea + "))) = 'TRUE'";
		updateJText(query); 
		ResultSet rsp = db.getResultSet(query);
		draw_ANNOUNCEMENTS(rsp,Color.red);
		db.close();
	}


	/* ---------------DRAW_____ANNOUNCEMENTS------------------- */
	public void draw_ANNOUNCEMENTS(ResultSet rs, Color c){
		try {
			boolean firstone = false;
			while(rs.next())
			{
				Graphics g = lblNewLabel.getGraphics();
				if(rdbtnPointQuery.isSelected()&&!firstone)
				{
					g.setColor(Color.yellow);
					firstone = true;
				}
				else  g.setColor(c);
				int r = rs.getInt("RADIUS");
				int x = rs.getInt("LOC.SDO_POINT.x");
				int y = rs.getInt("LOC.SDO_POINT.y");
				g.fillRect(x-15/2, y-15/2, 15, 15);
				g.drawOval(x-r, y-r, 2*r, 2*r);
				g.dispose();
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}


	/* ---------------WHOLE REGION BUILDINGS------------------- */
	public void wholeRegion_BUILDINGS() {
		DBexecute db = new DBexecute();
		db.connect();
		String query = "SELECT b.NUM_VERTICES, b.LOC.SDO_ORDINATES   FROM BUILDINGS b";
		updateJText(query); 
		ResultSet rsp = db.getResultSet(query);
		draw_BUILDINGS(rsp,Color.yellow);
		db.close();
	}


	/* ---------------POINT_QUERY_BUILDINGS------------------- */
	public void POINT_QUERY_BUILDINGS(String circle) {
		DBexecute db = new DBexecute();
		db.connect();
		String query = "SELECT b.NUM_VERTICES, b.LOC.SDO_ORDINATES   FROM BUILDINGS b WHERE SDO_ANYINTERACT(b.AREA,SDO_GEOMETRY"
				+ "(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,4),SDO_ORDINATE_ARRAY(" + circle + ")))='TRUE'"
				+ "ORDER BY SDO_GEOM.SDO_DISTANCE(b.AREA, SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE("
				+ clicked_x + ", " + clicked_y + ", NULL), NULL, NULL), 1)";
		updateJText(query); 
		ResultSet rsp = db.getResultSet(query);
		draw_BUILDINGS(rsp,Color.green);
		db.close();
	}

	
	/* ---------------RANGE_QUERY_BUILDINGS------------------- */ 
	public void RANGE_QUERY_BUILDINGS(String rangeArea){
		DBexecute db = new DBexecute();
		db.connect();
		String query = "SELECT b.NUM_VERTICES, b.LOC.SDO_ORDINATES   FROM BUILDINGS b WHERE SDO_ANYINTERACT(b.AREA,SDO_GEOMETRY"
				+ "(2003,NULL,NULL,SDO_ELEM_INFO_ARRAY(1,1003,1),SDO_ORDINATE_ARRAY(" + rangeArea + ")))='TRUE'";
		updateJText(query); 
		ResultSet rsp = db.getResultSet(query);
		draw_BUILDINGS(rsp,Color.yellow);
		db.close();
	}


	/* ---------------DRAW_____BUILDINGS------------------- */
	public void draw_BUILDINGS(ResultSet rs, Color c){
		try {
			boolean firstone = false;
			while(rs.next())
			{
				Graphics g = lblNewLabel.getGraphics();
				if(rdbtnPointQuery.isSelected()&&!firstone)
				{
					g.setColor(Color.yellow);
					firstone = true;
				}
				else  g.setColor(c);
				int numver=rs.getInt("NUM_VERTICES");
				Array loc = ((OracleResultSet)rs).getArray("LOC.SDO_ORDINATES");
				Number[] xandy= (Number[]) loc.getArray();
				int[] x_array=new int[xandy.length/2];
				int[] y_array=new int[xandy.length/2];
				for(int i = 0;i < xandy.length;i++)
				{
					if(i % 2 == 0)
					{
						x_array[i/2]=xandy[i].intValue();
					}
					else
					{
						y_array[i/2]=xandy[i].intValue();
					}
				}
				g.drawPolygon(x_array,y_array,numver);
				g.dispose();
			}
			rs.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



	/* ---------------EMERGENCY_QUERY------------------- */
	public void EMERGENCY_QUERY() throws SQLException{

		HashMap<String,Color> AScolor = new HashMap<String,Color>();
		AScolor.put("a1psa",Color.pink);
		AScolor.put("a2ohe",Color.orange);
		AScolor.put("a3sgm",Color.blue);
		AScolor.put("a4hnb",Color.cyan);
		AScolor.put("a5vhe",Color.green);
		AScolor.put("a6ssc",Color.MAGENTA);
		AScolor.put("a7helen",Color.gray);

		HashMap<String,ArrayList<String>> asSID = new HashMap<String,ArrayList<String>>();
		DBexecute db1 = new DBexecute();
		db1.connect();
		String query1 = "SELECT a.* FROM ANNOUNCEMENTSYSTEMS a WHERE SDO_NN(a.AREA, SDO_GEOMETRY(2001, NULL, SDO_POINT_TYPE("
				+ clicked_x + ", " + clicked_y + ", NULL), NULL, NULL), 'sdo_num_res=1') = 'TRUE'"; 
		ResultSet rsp1 = db1.getResultSet(query1);
		String brokenAS="";
		while(rsp1.next())
		{
			brokenAS =rsp1.getString("asID");
		}
		rsp1.close();
		db1.close();

		DBexecute db2 = new DBexecute();
		db2.connect();
		String query2 = "SELECT s.* FROM STUDENTS s, ANNOUNCEMENTSYSTEMS a "
				+ "WHERE SDO_INSIDE(s.LOC,a.AREA) = 'TRUE' AND a.asID ='" + brokenAS + "'" ;
		updateJText(query2);  
		ResultSet rsp2 = db2.getResultSet(query2);  
		while(rsp2.next())
		{
			ArrayList<String> stu = new ArrayList<String>();
			String sid = rsp2.getString("personID");
			DBexecute db3 = new DBexecute();
			db3.connect();
			String query3 = "SELECT a2.asID, a2.LOC.SDO_POINT.x, a2.LOC.SDO_POINT.y, a2.RADIUS FROM ANNOUNCEMENTSYSTEMS a1,ANNOUNCEMENTSYSTEMS a2, STUDENTS s WHERE s.personID = '" + sid +"'"
					+ " AND a1.asID ='" + brokenAS + "' AND a2.asID < > a1.asID" + " AND SDO_NN(a2.AREA, s.LOC, 'sdo_num_res=2') = 'TRUE'";
			updateJText(query3);  
			ResultSet rsp3 = db3.getResultSet(query3);
			String secAS="";
			while(rsp3.next())
			{
				secAS =rsp3.getString("asID");
				if(asSID.containsKey(secAS))
				{
					stu = asSID.get(secAS);
					stu.add(sid);
				}
				else
				{
					stu.add(sid);
				}
				asSID.put(secAS,stu);
				Color c = AScolor.get(secAS);
				Graphics g = lblNewLabel.getGraphics();
				g.setColor(c);
				int r = rsp3.getInt("RADIUS");
				int x = rsp3.getInt("LOC.SDO_POINT.x");
				int y = rsp3.getInt("LOC.SDO_POINT.y");
				g.fillRect(x-15/2, y-15/2, 15, 15);
				g.drawOval(x-r, y-r, 2*r, 2*r);
				g.dispose();
			}
			rsp3.close();
			db3.close();
		}
		rsp2.close();
		db2.close();
		Set<String> keyset = asSID.keySet(); 
		for(String key:keyset)
		{
			ArrayList<String> studentlist = asSID.get(key);
			Color c = AScolor.get(key);
			for(int i=0;i<studentlist.size();i++)
			{
				DBexecute db4 = new DBexecute();
				db4.connect();
				String stu_ID = studentlist.get(i);
				String query = "SELECT  s.LOC.SDO_POINT.x,s.LOC.SDO_POINT.y FROM STUDENTS s WHERE s.personID ='" + stu_ID + "'"; 
				updateJText(query); 
				ResultSet rsp = db4.getResultSet(query);
				draw_STUDENTS(rsp,c);
				db4.close();
			}
		}

	}	
}




