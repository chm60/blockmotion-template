package uk.ac.aber.dcs.blockmotion.Transformations;

import uk.ac.aber.dcs.blockmotion.Transform;
import uk.ac.aber.dcs.blockmotion.model.IFrame;

/**
 * Created by chris on 07/05/17.
 */
public class SlideL extends Transform {

    public void transform(IFrame frame){

    int rowsCols = frame.getNumRows();
    String[] data = new String[rowsCols*rowsCols+1];


    //fill an array from the frames array in reverse row order
    int q = 0;

    for (int i = 0; i < rowsCols; i++){



        for (int j = 0; j < rowsCols; j++){
            //Will need to do row by row

            data[q] = Character.toString(frame.getChar(i,j+1));
            q++;
        }

        data[(i*rowsCols)+(rowsCols-1)] = Character.toString(frame.getChar(i,0));


    }


    int z = 0;
    //fills frame with new flipped data
    for(int i=0; i<rowsCols;i++){

        for(int j=0;j<rowsCols;j++){

            frame.setChar(i,j,data[z].charAt(0));
            z++;
        }

    }




}
}
