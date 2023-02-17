import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;
import javax.swing.table.DefaultTableModel;



public class GetInfos {

	HashMap<Integer, String> compare = new HashMap<>();
	HashMap<Integer, String> start = new HashMap<>();
	String a1[]=new String[5];
	String storepid;
	String storename;
	int pidtable;
	String nametable;
	Set<String> difference;
	Set<String> end;
	Process p ;
	boolean stop=false;
	static int pid1;
	static String processName1;
	int disppid;
	String Dispname;
	String datetime;


	String[] processNames = { "AuActMon.exe", "AuDBServer.exe", "AuTray.exe", "AuUnpackExe.exe",
			"AuWatchDogService.exe", "AuScanner.exe", "AuFirewallSrv.exe", "AuMainUI.exe", "AuUsb.exe" };

	public void csvreader(DefaultTableModel model ,DefaultTableModel model1) throws IOException, InterruptedException
	{
		while(stop==false)
		{
			Process p = Runtime.getRuntime().exec("tasklist /fo csv /nh");
			BufferedReader reader = new BufferedReader(new InputStreamReader(p.getInputStream()));
			String line;

			while ((line = reader.readLine()) != null) {
				String[] processData = line.split(",");
				pid1 = Integer.parseInt(processData[1].replaceAll("[^0-9]", ""));
				processName1 = processData[0].replaceAll("\"", "");
				start.put(pid1, processName1);

				for (String processName : processNames) {

					if(processName1.equals(processName))
					{

						Object[] row= {pid1,processName1};
						model.addRow(row);

					}
				}
			}
			hashMapDifference();
			crashwriter(model1);
			difference.clear();
			compare.clear();
			start.clear();

			for (int g = 0; g < model.getRowCount(); g++)
			{
				try {
					pidtable = (Integer) model.getValueAt(g, 0);
					nametable=(String) model.getValueAt(g, 1);
					compare.put(pidtable, nametable);

				}
				catch(Exception e)
				{
					e.printStackTrace();
					System.out.println("Exception in capturing Rowss");
				}

			}
			Thread.sleep(800);
			for (int k = 0; k < a1.length; k++)
			{
				model.setRowCount(0);

			}
		}
	}

	public void hashMapDifference() throws IOException, InterruptedException {

		if(start.isEmpty()==true)
		{

		}
		else if(start.isEmpty()!=true)
		{

			difference= new HashSet(start.entrySet());
			end= new HashSet(compare.entrySet());
			end.removeAll(difference);

		}

	}

	public void crashwriter(DefaultTableModel model2) throws IOException, InterruptedException
	{
		Set<String> ProcessName =end ;
		String Crashes = null;
		if(ProcessName.isEmpty()==true)
		{

		}
		else if(ProcessName.isEmpty()!=true)
		{
			time();
			Object[] row = {ProcessName,datetime};
			model2.addRow(row);
			row=null;

		}
		System.gc();
	}

	public void time() {

		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
		LocalDateTime now = LocalDateTime.now();
		datetime=dtf.format(now);

	}
	public static void main(String[] args) throws IOException {



	}

}
