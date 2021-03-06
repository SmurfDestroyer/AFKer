package afk;

import org.powerbot.script.PaintListener;
import org.powerbot.script.PollingScript;
import org.powerbot.script.Script;
import org.powerbot.script.rt4.ClientContext;

import afk.node.Task;
import afk.scripts.AFK1;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Script.Manifest(name="Easy AFK", description="Keeps you logged in while you are doing other things.")
public class Main extends PollingScript<ClientContext> implements PaintListener{

	private List<Task> taskList = new ArrayList<Task>();
	private static Font font = new Font("Verdana", Font.PLAIN, 15);
	
    private long startTime = System.currentTimeMillis();
    private static final Color fontColor = Color.WHITE;
    
    private static int textAlignY = 14;
    
    int startX = 7;
    int startY = 345;
    int endX = 505;
    int endY = 128;
    
	@Override
    public void start() {
        taskList.addAll(Arrays.asList(new AFK1(ctx)));
    }

    @Override
    public void poll() {
    	for (Task task : taskList){
    		if (task.activate()){
    			task.execute();
    		}
    	}
    }
    
    public void repaint(final Graphics g) {
    	
    	Point m = ctx.input.getLocation();
    	
        int mX = (int) m.getX();
        int mY = (int) m.getY();
        
        /// Not Mouse
        g.setColor(Color.BLACK);
        g.drawRect(startX-1, startY-1, endX+1, endY+1);
    	g.setColor(Color.GRAY);
    	g.fillRect(startX, startY, endX, endY);
    	g.setColor(fontColor);
        g.setFont(font);
        g.drawString("Author: xXJAMYBOIXx", textAlignY, 465);
        g.drawString("Time Spent AFKing: " + runTime(startTime), textAlignY, 415);
        g.drawString("Status: " + AFK1.paintStatus, textAlignY, 365);
        
        /// Mouse
        g.drawLine(mX + 600, mY - 600, mX - 600, mY + 600);
        g.drawLine(mX + 600, mY + 600, mX - 600, mY - 600);
    	
    }
    
    private String runTime(long i) {
        DecimalFormat format = new DecimalFormat("00");
        long millis = System.currentTimeMillis() - i;
        long hours = millis / (1000 * 60 * 60);
        millis -= hours * (1000 * 60 * 60);
        long minutes = millis / (1000 * 60);
        millis -= minutes * (1000 * 60);
        long seconds = millis / 1000;
        return format.format(hours) + ":" + format.format(minutes) + ":" + format.format(seconds);
    }
    
}