package pl.kielce.tu.worldyouthday.user.resources;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class ChangePasswordResource implements Serializable {

    private final String oldPassword;
    private final String newPassword;

    @JsonCreator
    public ChangePasswordResource(@JsonProperty("oldPassword") String oldPassword,
                                  @JsonProperty("newPassword") String newPassword) {

        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    private ChangePasswordResource(Builder builder) {
        this.oldPassword = builder.oldPassword;
        this.newPassword = builder.newPassword;
    }

    public static Builder newBuilder() {
        return new Builder();
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChangePasswordResource that = (ChangePasswordResource) o;

        if (oldPassword != null ? !oldPassword.equals(that.oldPassword) : that.oldPassword != null) return false;
        return !(newPassword != null ? !newPassword.equals(that.newPassword) : that.newPassword != null);

    }

    @Override
    public int hashCode() {
        int result = oldPassword != null ? oldPassword.hashCode() : 0;
        result = 31 * result + (newPassword != null ? newPassword.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "ChangePasswordResource{" +
                "oldPassword='" + oldPassword + '\'' +
                ", newPassword='" + newPassword + '\'' +
                '}';
    }

    public static class Builder {

        private String oldPassword;
        private String newPassword;

        public Builder withOldPassword(String oldPassword) {
            this.oldPassword = oldPassword;
            return this;
        }

        public Builder withNewPassword(String newPassword) {
            this.newPassword = newPassword;
            return this;
        }

        public ChangePasswordResource build() {
            return new ChangePasswordResource(this);
        }

    }

}
