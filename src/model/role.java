package model;

    public class role {
        private int roleid;
        private String rolename;

        public role(){}

        public role(int roleid, String rolename) {
            this.roleid = roleid;
            this.rolename = rolename;
        }
        public int getroleid()
        {
            return roleid;
        }
        public void setroleid(int roleid)
        {
            this.roleid = roleid;
        }
        public String getrolename()
        {
            return rolename;
        }
        public void setrolename(String rolename)
        {
            this.rolename = rolename;
        }
        @Override
        public String toString()
        {
            return "Role{" +
                    "roleId=" + roleid +
                    ", roleName='" + rolename + '\'' +
                    '}';
        }
    }
