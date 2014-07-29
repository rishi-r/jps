package db;

import java.io.Closeable;
import java.sql.*;

public class Connect {

    String username = "root";
    String password = "";
    String url = "jdbc:mysql://localhost:3306/";
    String db = "resumedb";
    private Connection conn;
    PreparedStatement stmt;
    Statement cstmt;
    ResultSet rs;

    public void connect() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            conn = (Connection) DriverManager.getConnection(url+db, username, password);
        } catch (Exception e) {
            System.out.println("not connected" + e.getMessage());
        }
    }//end of Connection()

    public ResultSet login(String name, String pass) {
        connect();
        try {
            rs = null;
            PreparedStatement ps = conn.prepareStatement("select * from userlogin where userid=? and user_password=?");
            ps.setString(1, name);
            ps.setString(2, pass);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println("not Inserted" + e.getMessage());
        }
        return rs;
    }//end of category_search   SELECT city_name FROM city WHERE country_id=4

    public ResultSet city_name_search(int sttr) {

        connect();
        try {
            rs = null;
            PreparedStatement ps = conn.prepareStatement("select * from city where country_id=?");
            ps.setInt(1, sttr);

            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println("not Inserted" + e.getMessage());
        }
        return rs;
    }

    public ResultSet board() {
        connect();
        try {
            rs = null;
            stmt = conn.prepareStatement("select * from board_tab");
            //stmt.setString(1, name);
            rs = stmt.executeQuery();

        } catch (Exception e) {
            System.out.println("not Inserted" + e.getMessage());
        }
        return rs;
    }

    public ResultSet userdata(int sttr) {

        connect();
        try {
            rs = null;
            stmt = conn.prepareStatement("SELECT a.*,b.tot_exper_yr,b.tot_exper_mon,b.total_sal_lack,"
                    + "b.total_sal_thou,b.fun_area,b.industry,b.key_skills,b.resume_head,"
                    + "b.profile_obj,c.stream AS ten_stream,c.board_name AS ten_board_name,c.school_name "
                    + "AS ten_school_name,c.place AS ten_place,c.percentage AS ten_percentage,c.pass_year "
                    + "AS ten_pass_year,d.stream AS tw_stream,d.board_name AS tw_board_name,d.school_name "
                    + "AS tw_school_name,d.place AS tw_place,d.percentage AS tw_percentage,d.pass_year AS "
                    + "tw_pass_year,e.stream AS grad_stream,e.university AS grad_university,"
                    + "e.college_name AS grad_college_name,e.place AS grad_place,e.percentage"
                    + " AS grad_percentage,e.pass_year AS grad_pass_year,f.stream AS pgrad_stream,"
                    + "f.university AS pgrad_university,f.college_name AS pgrad_college_name,"
                    + "f.place AS pgrad_place,f.percentage AS pgrad_percentage,"
                    + "f.pass_year AS pgrad_pass_year FROM registration_tab "
                    + "AS a JOIN prof_info_tab AS b JOIN tenth_class_tab AS c "
                    + "JOIN twelth_class_tab AS d JOIN grad_tab AS e JOIN post_grad_tab AS f where "
                    + "a.reg_id=? AND b.reg_id=? AND c.reg_id=? AND d.reg_id=? AND e.reg_id=? AND "
                    + "f.reg_id=? ");
            stmt.setInt(1, sttr);
            stmt.setInt(2, sttr);
            stmt.setInt(3, sttr);
            stmt.setInt(4, sttr);
            stmt.setInt(5, sttr);
            stmt.setInt(6, sttr);
            rs = stmt.executeQuery();
        } catch (Exception e) {
            System.out.println("not Inserted" + e.getMessage());
        }
        return rs;
    }

    public ResultSet get_page1_data(int sttr) {

        connect();
        try {
            rs = null;
            stmt = conn.prepareStatement("SELECT * FROM registration_tab WHERE reg_id=? ");
            stmt.setInt(1, sttr);
            rs = stmt.executeQuery();
        } catch (Exception e) {
            System.out.println("not Inserted" + e.getMessage());
        }
        return rs;
    }

    public ResultSet ex_fr(int sttr) {

        connect();
        try {
            rs = null;
            stmt = conn.prepareStatement("SELECT ex_fr FROM job_tab WHERE reg_id=? ");
            stmt.setInt(1, sttr);
            rs = stmt.executeQuery();
        } catch (Exception e) {
            System.out.println("not Inserted" + e.getMessage());
        }
        return rs;
    }

    public ResultSet userdata2(int sttr) {

        connect();
        try {
            rs = null;
            stmt = conn.prepareStatement("SELECT * FROM job_tab WHERE reg_id=? ");
            stmt.setInt(1, sttr);
            rs = stmt.executeQuery();
        } catch (Exception e) {
            System.out.println("not Inserted" + e.getMessage());
        }
        return rs;
    }

    public String admin_delete_user(int id) {

        connect();
        try {
            rs = null;
            stmt = conn.prepareStatement("delete from registration_tab where reg_id=? ");
            stmt.setInt(1, id);
            stmt.executeUpdate();
        } catch (Exception e) {
            System.out.println("not Inserted" + e.getMessage());
            return "no";
        }
        return "yes";
    }

    public String admin_getall_user(int id) {

        connect();
        try {
            rs = null;
            stmt = conn.prepareStatement("Select * registration_tab where reg_id=? ");
            stmt.setInt(1, id);
            rs = stmt.executeQuery();
        } catch (Exception e) {
            System.out.println("not Inserted" + e.getMessage());
            return "no";
        }
        return "yes";
    }

    public ResultSet country() {
        connect();
        try {
            rs = null;
            stmt = conn.prepareStatement("select * from country ");
            //stmt.setString(1, name);
            rs = stmt.executeQuery();

        } catch (Exception e) {
            System.out.println("not Inserted" + e.getMessage());
        }

        return rs;
    }

    public String getstep_val(String u, String p) {
        connect();
        int str = 0;
        int str2 = 0;
        try {

            CallableStatement calstat = conn.prepareCall("{call getstep_val(?,?,?,?)}");
            calstat.setString(1, u);
            calstat.setString(2, p);

            calstat.registerOutParameter(3, java.sql.Types.INTEGER);
            calstat.registerOutParameter(4, java.sql.Types.INTEGER);

            calstat.executeQuery();
            str = calstat.getInt(3);
            str2 = calstat.getInt(4);

        } catch (Exception e) {
            System.out.println("not " + e.getMessage());
        }

        return str + "";
    }

    public ResultSet getCity(int cntry_id) {
        connect();
        try {
            rs = null;
            stmt = conn.prepareStatement("select * from city where country_id=?");
            stmt.setInt(1, cntry_id);
            rs = stmt.executeQuery();

        } catch (Exception e) {
            System.out.println("not get");
        }

        return rs;
    }

    public ResultSet getReg_user(String user, String pass) {
        connect();
        try {
            rs = null;
            stmt = conn.prepareStatement("select reg_id from userlogin where userid=? and user_password=?");
            stmt.setString(1, user);
            stmt.setString(2, pass);
            rs = stmt.executeQuery();

        } catch (Exception e) {
            System.out.println("not get");
        }

        return rs;
    }

    public ResultSet getValidUser(String nm) {

        connect();
        try {
            rs = null;
            stmt = conn.prepareStatement("select userid from userlogin where userid=? ");
            stmt.setString(1, nm);

            rs = stmt.executeQuery();
        } catch (Exception ee) {
            System.out.println("not Inserted" + ee.getMessage());
        }
        return rs;
    }

    public String insert_registration(String f_name, String l_name, String father_name, String gen,
            String calinput, String nationality, String country_id, String cty, String state, int zip_code,
            String address, String p_address, String ch_email, String tel_phone, String mob_phone,
            String u_name, String u_pass) {
        Date dt = new Date(0000 - 00 - 00);
        connect();
        try {

            CallableStatement calstat = conn.prepareCall("{call insert_registration(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            calstat.setString(1, f_name);
            calstat.setString(2, l_name);
            calstat.setString(3, father_name);
            calstat.setString(4, gen);
            calstat.setDate(5, dt.valueOf(calinput));
            calstat.setString(6, nationality);
            calstat.setString(7, country_id);
            calstat.setString(8, cty);
            calstat.setString(9, state);
            calstat.setInt(10, zip_code);
            calstat.setString(11, address);
            calstat.setString(12, p_address);
            calstat.setString(13, ch_email);
            calstat.setString(14, tel_phone);
            calstat.setString(15, mob_phone);
            calstat.setString(16, u_name);
            calstat.setString(17, u_pass);
            ResultSet rs = calstat.executeQuery();
        } catch (Exception ee) {
            System.out.println(ee);
            return "no";

        }
        return "yes";
    }

    public String insert_job_info(String ex_fr, String job_title1, String job_company1, int job_year11, int job_year21,
            String job_title2, String job_company2, int job_year12, int job_year22, int check_fr_ex_other, int regis_id) {
        Date dt = new Date(0000 - 00 - 00);
        connect();
        try {

            CallableStatement calstat = conn.prepareCall("{call insert_job_info(?,?,?,?,?,?,?,?,?,?,?)}");
            calstat.setInt(1, regis_id);
            calstat.setString(2, job_title1);
            calstat.setString(3, job_company1);
            calstat.setInt(4, job_year11);
            calstat.setInt(5, job_year21);
            calstat.setString(6, job_title2);
            calstat.setString(7, job_company2);
            calstat.setInt(8, job_year12);
            calstat.setInt(9, job_year22);
            calstat.setInt(10, check_fr_ex_other);
            calstat.setString(11, ex_fr);
            ResultSet rs = calstat.executeQuery();
        } catch (Exception ee) {
            System.out.println(ee);
            return "no";

        }
        return "yes";
    }

    public String insert_prof_info(int regis_id, String tot_exp, String tot_exp2, String cur_sal, String cur_sal2,
            String cur_industry, String fun_area, String key_skill, String reshead, String tenth,
            String ten1, String ten2, String ten3, String ten4, String ten5, String twelthth, String twelve1,
            String twelve2, String twelve3, String twelve4, String twelve5, String grad_edu, String grad_edu1, String grad_edu2,
            String grad_edu3, String grad_edu4, int grad_edu5, String p_grad_edu, String p_grad_edu1, String p_grad_edu2, String p_grad_edu3, String p_grad_edu4, int p_grad_edu5, String Profile_obj) {

        connect();
        try {

            CallableStatement calstat = conn.prepareCall("{call insert_prof_edu_detail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            calstat.setInt(1, regis_id);
            calstat.setString(2, tot_exp);
            calstat.setString(3, tot_exp2);
            calstat.setString(4, cur_sal);
            calstat.setString(5, cur_sal2);
            calstat.setString(6, cur_industry);
            calstat.setString(7, fun_area);
            calstat.setString(8, key_skill);
            calstat.setString(9, reshead);
            calstat.setString(10, tenth);
            calstat.setString(11, ten1);
            calstat.setString(12, ten2);
            calstat.setString(13, ten3);
            calstat.setString(14, ten4);
            calstat.setString(15, ten5);
            calstat.setString(16, twelthth);
            calstat.setString(17, twelve1);
            calstat.setString(18, twelve2);
            calstat.setString(19, twelve3);
            calstat.setString(20, twelve4);
            calstat.setString(21, twelve5);

            calstat.setString(22, grad_edu);
            calstat.setString(23, grad_edu1);
            calstat.setString(24, grad_edu2);
            calstat.setString(25, grad_edu3);
            calstat.setString(26, grad_edu4);
            calstat.setInt(27, grad_edu5);

            calstat.setString(28, p_grad_edu);
            calstat.setString(29, p_grad_edu1);
            calstat.setString(30, p_grad_edu2);
            calstat.setString(31, p_grad_edu3);
            calstat.setString(32, p_grad_edu4);
            calstat.setInt(33, p_grad_edu5);
            calstat.setString(34, Profile_obj);
            ResultSet rs = calstat.executeQuery();
        } catch (Exception ee) {
            System.out.println(ee);
            return "no";

        }
        return "yes";
    }

    public String update_registration(String f_name, String l_name, String father_name, String gen,
            String calinput, String nationality, String country_id, String cty, String state, int zip_code,
            String address, String p_address, String ch_email, String tel_phone, String mob_phone,
            int reg_id) {
        Date dt = new Date(0000 - 00 - 00);
        connect();
        try {

            CallableStatement calstat = conn.prepareCall("{call update_registration(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            calstat.setString(1, f_name);
            calstat.setString(2, l_name);
            calstat.setString(3, father_name);
            calstat.setString(4, gen);
            calstat.setDate(5, dt.valueOf(calinput));
            calstat.setString(6, nationality);
            calstat.setString(7, country_id);
            calstat.setString(8, cty);
            calstat.setString(9, state);
            calstat.setInt(10, zip_code);
            calstat.setString(11, address);
            calstat.setString(12, p_address);
            calstat.setString(13, ch_email);
            calstat.setString(14, tel_phone);
            calstat.setString(15, mob_phone);
            calstat.setInt(16, reg_id);

            ResultSet rs = calstat.executeQuery();
        } catch (Exception ee) {
            System.out.println(ee);
            return "no";

        }
        return "yes";
    }

    public String update_prof_info(int regis_id, String tot_exp, String tot_exp2, String cur_sal, String cur_sal2,
            String cur_industry, String fun_area, String key_skill, String reshead, String tenth,
            String ten1, String ten2, String ten3, String ten4, String ten5, String twelthth, String twelve1,
            String twelve2, String twelve3, String twelve4, String twelve5, String grad_edu, String grad_edu1, String grad_edu2,
            String grad_edu3, String grad_edu4, int grad_edu5, String p_grad_edu, String p_grad_edu1, String p_grad_edu2, String p_grad_edu3, String p_grad_edu4, int p_grad_edu5, String Profile_obj) {

        connect();
        try {

            CallableStatement calstat = conn.prepareCall("{call update_prof_edu_detail(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
            calstat.setInt(1, regis_id);
            calstat.setString(2, tot_exp);
            calstat.setString(3, tot_exp2);
            calstat.setString(4, cur_sal);
            calstat.setString(5, cur_sal2);
            calstat.setString(6, cur_industry);
            calstat.setString(7, fun_area);
            calstat.setString(8, key_skill);
            calstat.setString(9, reshead);
            calstat.setString(10, tenth);
            calstat.setString(11, ten1);
            calstat.setString(12, ten2);
            calstat.setString(13, ten3);
            calstat.setString(14, ten4);
            calstat.setString(15, ten5);
            calstat.setString(16, twelthth);
            calstat.setString(17, twelve1);
            calstat.setString(18, twelve2);
            calstat.setString(19, twelve3);
            calstat.setString(20, twelve4);
            calstat.setString(21, twelve5);

            calstat.setString(22, grad_edu);
            calstat.setString(23, grad_edu1);
            calstat.setString(24, grad_edu2);
            calstat.setString(25, grad_edu3);
            calstat.setString(26, grad_edu4);
            calstat.setInt(27, grad_edu5);

            calstat.setString(28, p_grad_edu);
            calstat.setString(29, p_grad_edu1);
            calstat.setString(30, p_grad_edu2);
            calstat.setString(31, p_grad_edu3);
            calstat.setString(32, p_grad_edu4);
            calstat.setInt(33, p_grad_edu5);
            calstat.setString(34, Profile_obj);
            ResultSet rs = calstat.executeQuery();
        } catch (Exception ee) {
            System.out.println(ee);
            return "no";

        }
        return "yes";
    }

    // you need to close all three to make sure
    public void close() {
        close((Closeable) rs);
        close((Closeable) cstmt);
        close((Closeable) conn);
    }

    private void close(Closeable c) {

        try {
            if (c != null) {
                c.close();
            }
        } catch (Exception e) {
            // don't throw now as it might leave following closables in undefined state
        }
    }
}//end of Connection

