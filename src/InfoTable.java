import java.awt.Dimension;
import java.awt.FlowLayout;

import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class InfoTable extends JFrame{
	
	JTable table;
	
	public InfoTable(){
		
		setLayout(new FlowLayout()); //not sure what this does
		
		
		String[] columnNames = {"Name", "Score"}; 
		
		Object [][] data = {
				{"Green", 1},
				{"Red", 2},
				{"Blue", 3}
		};
		
		table = new JTable(data, columnNames);
		table.setPreferredScrollableViewportSize(new Dimension(500,50));
		table.setFillsViewportHeight(true);
		
		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane);
		
		
	}
	
	

}
