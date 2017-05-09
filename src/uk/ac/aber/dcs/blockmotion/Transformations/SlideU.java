package uk.ac.aber.dcs.blockmotion.Transformations;

import uk.ac.aber.dcs.blockmotion.Transform;
import uk.ac.aber.dcs.blockmotion.model.IFrame;

/**
 * Created by chris on 08/05/17.
 */
public class SlideU extends Transform {

    public void transform(IFrame frame){

        int rowsCols = frame.getNumRows();
        String[] data = new String[rowsCols*rowsCols];


        //fill an array from the frames array in reverse row order
        int q = 0;

        for (int i = 1; i < rowsCols; i++){


            for (int j = 0; j < rowsCols; j++){
                //Will need to do row by row

                data[q] = Character.toString(frame.getChar(i,j));

                data[(rowsCols*rowsCols)-rowsCols+j] = Character.toString(frame.getChar(0,j));
                q++;
            }


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
