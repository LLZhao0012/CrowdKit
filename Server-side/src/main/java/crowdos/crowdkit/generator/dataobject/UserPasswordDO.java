package crowdos.crowdkit.generator.dataobject;

public class UserPasswordDO {
    private Integer id;

    private Integer userId;

    private String encrptPassword;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getEncrptPassword() {
        return encrptPassword;
    }

    public void setEncrptPassword(String encrptPassword) {
        this.encrptPassword = encrptPassword == null ? null : encrptPassword.trim();
    }

    @Override
    public String toString() {
        return "UserPasswordDO{" +
                "id=" + id +
                ", userId=" + userId +
                ", encrptPassword='" + encrptPassword + '\'' +
                '}';
    }
}