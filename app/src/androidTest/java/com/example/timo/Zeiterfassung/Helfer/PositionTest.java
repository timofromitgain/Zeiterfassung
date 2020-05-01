package com.example.timo.Zeiterfassung.Helfer;

import com.example.timo.Zeiterfassung.Beans.Position;

import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import static org.junit.Assert.assertEquals;

public class PositionTest extends android.test.AndroidTestCase {







    @Test
    public void testArbeitszeit() {
        Position position = new Position("");
        Double az;
        Date anfangszeit = new Date();
        Date endzeit = new Date();
        anfangszeit.setHours(4);
        anfangszeit.setMinutes(56);
        endzeit.setHours(8);
        endzeit.setMinutes(3);
       az =  position.ermittleArbeitsZeitPosition(anfangszeit, endzeit);
       assertEquals(0.1,az,0.001);


    }
}