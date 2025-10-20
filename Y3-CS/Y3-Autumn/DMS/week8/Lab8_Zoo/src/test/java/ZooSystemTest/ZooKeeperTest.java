package ZooSystemTest;

import ZooSystem.ZooKeeper;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class ZooKeeperTest {
    @Test
    public void test_SetSalary(){
        ZooKeeper zk = new ZooKeeper("Jamie");
        zk.setSalary(100);
        assertEquals(100, zk.getSalary());
    }
}