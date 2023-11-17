package com.whut;

import org.junit.Test;

import com.whut.utils.Config;



public class AppTest{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {

       Config config = new Config();
       System.out.println(config.get("user_filepath"));
       System.out.println("d");

    }
}
