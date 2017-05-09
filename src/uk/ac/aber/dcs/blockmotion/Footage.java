package uk.ac.aber.dcs.blockmotion;

import uk.ac.aber.dcs.blockmotion.Transformations.FlipH;
import uk.ac.aber.dcs.blockmotion.model.IFootage;
import uk.ac.aber.dcs.blockmotion.model.IFrame;
import uk.ac.aber.dcs.blockmotion.model.Transformer;

import java.io.*;
import java.util.ArrayList;

/**
 * Created by chris on 27/04/17.
 */

public class Footage implements IFootage {
    ArrayList<IFrame> frames = new ArrayList<>(); //Array List to hold Frames
    private int fF; //# Frames in footage
    private int fRC; //# Rows and columns in footage
    String[] footageFrameVals;
    private FlipH flipH;


    @Override
    public int getNumFrames() {
        return fF;
    }

    @Override
    public int getNumRows() {
        return fRC;
    }

    @Override
    public IFrame getFrame(int num) {

        return frames.get(num);
    }

    @Override
    public void add(IFrame f) {
        frames.add(f);
    }

    @Override
    public void load(String fn) throws IOException {

        FileInputStream is = new FileInputStream(fn);
        BufferedReader bfr = new BufferedReader(new InputStreamReader(is));

        fF = Integer.parseInt(bfr.readLine());
        fRC = Integer.parseInt(bfr.readLine());

        for (int x = 0; x < fF; x++) {

            Frame newFrame = new Frame();
            footageFrameVals = new String[(fRC * fRC) + 1];
            int q = 0;

            for (int z = 0; z < fRC; z++) {

                String newLine = bfr.readLine();
                String[] newLineArray = newLine.split("(?!^)");


                for (int y = 0; y < fRC; y++) {


                    footageFrameVals[q] = newLineArray[y];

                    q++;
                }

            }
            footageFrameVals[q] = String.valueOf(fRC);
            newFrame.insertLines(footageFrameVals);
            frames.add(newFrame);

        }

    }

    @Override
    public void save(String fn) throws IOException {

        PrintWriter output = new PrintWriter(fn);

        output.print(fF + "\r\n");
        output.print(fRC + "\r\n");

        for (IFrame frame : frames){
            frame.tofile(output);
        }
        output.close();
    }

    @Override
    public void transform(Transformer t) {

        for (IFrame fa : frames) {

                t.transform(fa);

        }

    }


}
