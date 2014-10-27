package util.jayflot.spider;
import br.blog.arruda.plot.opt.PlotGrid;

public class SpiderPlotGrid extends PlotGrid{
	private String mode;
	
	public SpiderPlotGrid(){
		this.mode = "radar";
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}
}
