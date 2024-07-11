const Url = "http://localhost:9193";
export const properties = {
    loginURL : `${Url}/api/v1/auth/login`,
    sendMail : `${Url}/api/v1/mail/SendMail`,
    signUpUrl : `${Url}/api/v1/mail/signUp`,
    sendMailWithoutAttachments : `${Url}/api/v1/mail/SendMailWOA`,
    sendMassMail : `${Url}/api/v1/mail/upload-excel`,
    getCredentails : `${Url}/api/v1/mail/get-mail-credentials`,
    getFolders : `${Url}/api/v1/mail/get-all-folders`,
    deleteFolder : `${Url}/api/v1/mail/delete-folder`,

}