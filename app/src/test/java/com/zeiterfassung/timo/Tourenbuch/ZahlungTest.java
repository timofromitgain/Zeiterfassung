package com.zeiterfassung.timo.Zeiterfassung;

import android.content.Context;

import com.zeiterfassung.timo.Zeiterfassung.Helfer.Kunde;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;

import java.util.Calendar;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class ZahlungTest {
    Zahlung zahlung;
    Calendar tagHeute;
    Calendar tagHeute2;
    Kunde kunde;

    @Mock
    Context mockContext;

    @Before
    public void setUp() {
        Context context;
        zahlung = new Zahlung();
        tagHeute = Calendar.getInstance();
        tagHeute2 = Calendar.getInstance();
        tagHeute2.set(2019, 2, 31);

    }


    @Test
    public void kundeNachrichtRechnungszahler() {
        tagHeute.set(2019, 2, 3);
        assertEquals(false, zahlung.kundeNotification("Rechnungszahler",
                null,
                null,
                "06.01.2019", tagHeute));
    }

    @Test
    public void kundeNachrichtBz() {
        tagHeute.set(2019, 2, 3);
        assertEquals(true, zahlung.kundeNotification("Barzahler",
                "Woechentlich",
                null,
                "06.01.2019", tagHeute));
    }

    @Test
    public void kundeNachrichtBzMonatlich_1() {
        tagHeute.set(2019, 2, 3);
        assertEquals(true, zahlung.kundeNotification("Barzahler",
                "Monatlich",
                "Monatsanfang",
                "06.01.2019", tagHeute));
    }

    @Test
    public void kundeNachrichtBzMonatlich_2() {
        tagHeute.set(2019, 2, 3);
        assertEquals(true, zahlung.kundeNotification("Barzahler",
                "Monatlich",
                "Monatsende",
                "13.01.2019", tagHeute2));
    }

    @Test
    public void kundeNachrichtBzMonatlich2_1() {
        tagHeute.set(2019, 2, 3);
        assertEquals(true, zahlung.kundeNotification("Barzahler",
                "2-Monatlich",
                "Monatsanfang",
                "06.01.2019", tagHeute));
    }

    @Test
    public void kundeNachrichtBzMonatlich2_2() {
        Calendar tagHeute = Calendar.getInstance();
        tagHeute.set(2019, 4, 5);
        assertEquals(true, zahlung.kundeNotification("Barzahler",
                "2-Monatlich",
                "Monatsanfang",
                "06.01.2019", tagHeute));
    }

    @Test
    public void kundeNachrichtBzMonatlich2_3() {
        Calendar tagHeute = Calendar.getInstance();
        tagHeute.set(2019, 2, 31);
        assertEquals(true, zahlung.kundeNotification("Barzahler",
                "2-Monatlich",
                "Monatsende",
                "06.01.2019", tagHeute));
    }

    @Test
    public void kundeNachrichtBzMonatlich2_4() {
        Calendar tagHeute = Calendar.getInstance();
        tagHeute.set(2019, 3, 31);
        assertEquals(false, zahlung.kundeNotification("Barzahler",
                "2-Monatlich",
                "Monatsende",
                "06.01.2019", tagHeute));
    }

    @Test
    public void kundeNachrichtBzMonatlich2_5() {
        Calendar tagHeute = Calendar.getInstance();
        tagHeute.set(2019, 3, 14);
        assertEquals(false, zahlung.kundeNotification("Barzahler",
                "2-Monatlich",
                "Monatsende",
                "06.01.2019", tagHeute));
    }

    @Test
    public void kundeNachrichtBzMonatlich3_1() {
        tagHeute.set(2019, 3, 7);
        assertEquals(true, zahlung.kundeNotification("Barzahler",
                "3-Monatlich",
                "Monatsanfang",
                "06.01.2019", tagHeute));


    }

    @Test
    public void kundeNachrichtBzMonatlich3_2() {
        Calendar tagHeute = Calendar.getInstance();
        tagHeute.set(2019, 2, 3);
        assertEquals(false, zahlung.kundeNotification("Barzahler",
                "3-Monatlich",
                "Monatsanfang",
                "06.01.2019", tagHeute));
    }


    @Test
    public void kundeNachrichtBzMonatlich3_3() {
        Calendar tagHeute = Calendar.getInstance();
        tagHeute.set(2019, 2, 31);
        assertEquals(false, zahlung.kundeNotification("Barzahler",
                "3-Monatlich",
                "Monatsende",
                "06.01.2019", tagHeute));
    }

    @Test
    public void kundeNachrichtBzMonatlich3_4() {
        Calendar tagHeute = Calendar.getInstance();
        tagHeute.set(2019, 3, 28);
        assertEquals(true, zahlung.kundeNotification("Barzahler",
                "3-Monatlich",
                "Monatsende",
                "06.01.2019", tagHeute));
    }

    @Test
    public void kundeNachrichtBzMonatlich3_5() {
        Calendar tagHeute = Calendar.getInstance();
        tagHeute.set(2019, 4, 14);
        assertEquals(false, zahlung.kundeNotification("Barzahler",
                "3-Monatlich",
                "Monatsende",
                "06.01.2019", tagHeute));
    }


    @Test
    public void bezahlungRechnungszahler() {
        when(mockContext.getString(R.string.rechnungszahler)).thenReturn("Rechnungszahler");
        Zahlung zahlung = new Zahlung(mockContext);
        kunde = new Kunde("Rechnungszahler", null, null);
        String ergenis = zahlung.getBezahlung(kunde);
        assertEquals("Rechnungszahler", ergenis);
    }

    @Test
    public void bezahlungWoechentlich() {
        when(mockContext.getString(R.string.barzahler)).thenReturn("Barzahler");
        when(mockContext.getString(R.string.woechentlich)).thenReturn("Woechentlich");
        Zahlung zahlung = new Zahlung(mockContext);
        kunde = new Kunde("Barzahler", "Woechentlich", null);
        String ergenis = zahlung.getBezahlung(kunde);
        assertEquals("Barzahler\n" +
                "Woechentlich", ergenis);

    }

    @Test
    public void bezahlungMonatlich() {
        when(mockContext.getString(R.string.barzahler)).thenReturn("Barzahler");
        when(mockContext.getString(R.string.monatlich)).thenReturn("Monatlich");
        when(mockContext.getString(R.string.monatsanfang)).thenReturn("Monatsanfang");
        Zahlung zahlung = new Zahlung(mockContext);
        kunde = new Kunde("Barzahler", "Monatlich", "Monatsanfang");
        String ergenis = zahlung.getBezahlung(kunde);
        assertEquals("Barzahler\n" +
                "Monatlich\n" +
                "Monatsanfang", ergenis);

    }


    @After
    public void tearDown() {
    }


}