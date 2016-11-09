package de.baumann.pdfcreator.helper;

import android.app.Activity;
import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.support.design.widget.Snackbar;
import android.support.v4.content.FileProvider;
import android.text.Html;
import android.text.SpannableString;
import android.text.util.Linkify;
import android.view.View;
import android.webkit.MimeTypeMap;


import java.io.File;

import de.baumann.pdfcreator.R;
import filechooser.ChooserDialog;

public class Helper {

    public static void openFile (Activity activity, File file, String string, View view) {

        Intent intent = new Intent();
        intent.setAction(Intent.ACTION_VIEW);
        intent.setFlags(Intent.FLAG_ACTIVITY_NO_HISTORY);

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            intent.setFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            Uri contentUri = FileProvider.getUriForFile(activity, activity.getApplicationContext().getPackageName() + ".provider", file);
            intent.setDataAndType(contentUri,string);

        } else {
            intent.setDataAndType(Uri.fromFile(file),string);
        }

        try {
            activity.startActivity (intent);
        } catch (ActivityNotFoundException e) {
            Snackbar.make(view, R.string.toast_install_app, Snackbar.LENGTH_LONG).show();
        }
    }

    public static void openFilePicker (final Activity activity, final View view) {

        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(activity);
        String folder = sharedPref.getString("folder", "/Android/data/de.baumann.pdf/");

        new ChooserDialog().with(activity)
                .withStartFile(Environment.getExternalStorageDirectory() + folder)
                .withChosenListener(new ChooserDialog.Result() {
                    @Override
                    public void onChoosePath(File pathFile) {
                        String fileExtension = MimeTypeMap.getFileExtensionFromUrl(pathFile.toString().replace(" ", ""));
                        String text = (activity.getString(R.string.toast_extension) + ": " + fileExtension);

                        if(fileExtension != null) {
                            //do something else
                            switch (fileExtension) {
                                case "gif":
                                case "bmp":
                                case "tiff":
                                case "svg":
                                case "png":
                                case "jpg":
                                case "jpeg":
                                    Helper.openFile(activity, pathFile, "image/*", view);
                                    break;
                                case "m3u8":
                                case "mp3":
                                case "wma":
                                case "midi":
                                case "wav":
                                case "aac":
                                case "aif":
                                case "amp3":
                                case "weba":
                                    Helper.openFile(activity, pathFile, "audio/*", view);
                                    break;
                                case "mpeg":
                                case "mp4":
                                case "ogg":
                                case "webm":
                                case "qt":
                                case "3gp":
                                case "3g2":
                                case "avi":
                                case "f4v":
                                case "flv":
                                case "h261":
                                case "h263":
                                case "h264":
                                case "asf":
                                case "wmv":
                                    Helper.openFile(activity, pathFile, "video/*", view);
                                    break;
                                case "rtx":
                                case "csv":
                                case "txt":
                                case "vcs":
                                case "vcf":
                                case "css":
                                case "html":
                                case "ics":
                                case "conf":
                                case "java":
                                    Helper.openFile(activity, pathFile, "text/*", view);
                                    break;
                                case "apk":
                                    Helper.openFile(activity, pathFile, "application/vnd.android.package-archive", view);
                                    break;
                                case "pdf":
                                    Helper.openFile(activity, pathFile, "application/pdf", view);
                                    break;
                                case "doc":
                                    Helper.openFile(activity, pathFile, "application/msword", view);
                                    break;
                                case "xls":
                                    Helper.openFile(activity, pathFile, "application/vnd.ms-excel", view);
                                    break;
                                case "ppt":
                                    Helper.openFile(activity, pathFile, "application/vnd.ms-powerpoint", view);
                                    break;
                                case "docx":
                                    Helper.openFile(activity, pathFile, "application/vnd.openxmlformats-officedocument.wordprocessingml.document", view);
                                    break;
                                case "pptx":
                                    Helper.openFile(activity, pathFile, "application/vnd.openxmlformats-officedocument.presentationml.presentation", view);
                                    break;
                                case "xlsx":
                                    Helper.openFile(activity, pathFile, "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet", view);
                                    break;
                                case "odt":
                                    Helper.openFile(activity, pathFile, "application/vnd.oasis.opendocument.text", view);
                                    break;
                                case "ods":
                                    Helper.openFile(activity, pathFile, "application/vnd.oasis.opendocument.spreadsheet", view);
                                    break;
                                case "odp":
                                    Helper.openFile(activity, pathFile, "application/vnd.oasis.opendocument.presentation", view);
                                    break;
                                case "zip":
                                    Helper.openFile(activity, pathFile, "application/zip", view);
                                    break;
                                case "rar":
                                    Helper.openFile(activity, pathFile, "application/x-rar-compressed", view);
                                    break;
                                case "epub":
                                    Helper.openFile(activity, pathFile, "application/epub+zip", view);
                                    break;
                                case "cbz":
                                    Helper.openFile(activity, pathFile, "application/x-cbz", view);
                                    break;
                                case "cbr":
                                    Helper.openFile(activity, pathFile, "application/x-cbr", view);
                                    break;
                                case "fb2":
                                    Helper.openFile(activity, pathFile, "application/x-fb2", view);
                                    break;
                                case "rtf":
                                    Helper.openFile(activity, pathFile, "application/rtf", view);
                                    break;
                                case "opml":
                                    Helper.openFile(activity, pathFile, "application/opml", view);
                                    break;

                                default:
                                    Snackbar.make(view, text, Snackbar.LENGTH_LONG).show();
                                    break;
                            }
                        } else {
                            //do something else
                            Snackbar.make(view, R.string.toast_extension, Snackbar.LENGTH_LONG).show();
                        }

                    }
                })
                .build()
                .show();
    }

    public static SpannableString textSpannable (String text) {
        SpannableString s;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            s = new SpannableString(Html.fromHtml(text,Html.FROM_HTML_MODE_LEGACY));
        } else {
            //noinspection deprecation
            s = new SpannableString(Html.fromHtml(text));
        }
        Linkify.addLinks(s, Linkify.WEB_URLS);
        return s;
    }
}
